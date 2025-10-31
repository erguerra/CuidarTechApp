package br.com.cuidartech.app.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cuidartech.app.data.NursingProcessRepository
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.domain.model.NursingProcess
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.domain.model.SubjectFeatures
import br.com.cuidartech.app.ui.strings.AppStrings
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeSearchViewModel(
    private val subjectRepository: SubjectRepository,
    private val nursingProcessRepository: NursingProcessRepository,
) : ViewModel() {
    private val _searchState = MutableStateFlow<HomeSearchState>(HomeSearchState.Idle)
    val searchState: StateFlow<HomeSearchState> = _searchState.asStateFlow()

    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())
    val subjects: StateFlow<List<Subject>> = _subjects.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _filters = MutableStateFlow(HomeSearchState.AppliedFilters())
    val filters: StateFlow<HomeSearchState.AppliedFilters> = _filters.asStateFlow()

    private var searchJob: Job? = null
    private val cache = SearchCache()

    fun setSubjects(subjectList: List<Subject>) {
        // Cache the subjects so the sheet can build subject filters without re-fetching.
        _subjects.value = subjectList
    }

    fun onQueryChange(query: String, types: Set<HomeSearchState.ResultType>, subjectId: String?) {
        val filters = HomeSearchState.AppliedFilters(subjectId = subjectId, types = types)
        cache.requestedFilters = filters
        _filters.update { filters }
        _query.update { query }
        searchJob?.cancel()
        if (query.isBlank()) {
            _searchState.value = HomeSearchState.Idle
            return
        }
        searchJob = viewModelScope.launch {
            executeSearch(query)
        }
    }

    fun onFiltersChange(query: String, types: Set<HomeSearchState.ResultType>, subjectId: String?) {
        val filters = HomeSearchState.AppliedFilters(subjectId = subjectId, types = types)
        cache.requestedFilters = filters
        _filters.value = filters
        if (query.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            executeSearch(query)
        }
    }

    private fun executeSearch(query: String) {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) {
            _searchState.value = HomeSearchState.Idle
            return
        }
        _searchState.value = HomeSearchState.Loading
        viewModelScope.launch {
            val filters = cache.requestedFilters
            val subjectList = _subjects.value
            val subjectLookup = subjectList.associateBy { it.id }
            ensureBaseDataLoaded(filters.subjectId, subjectList)

            val results = collectResults(normalizedQuery.lowercase(), filters, subjectLookup)
            if (results.isEmpty()) {
                _searchState.value = HomeSearchState.Results(
                    query = normalizedQuery,
                    items = emptyList(),
                    appliedFilters = filters,
                )
            } else {
                _searchState.value = HomeSearchState.Results(
                    query = normalizedQuery,
                    items = results,
                    appliedFilters = filters,
                )
            }
        }
    }

    private suspend fun ensureBaseDataLoaded(subjectFilter: String?, subjectList: List<Subject>) {
        // Load each dataset on demand so we avoid repeated network calls while the user keeps searching.
        if (!cache.processesLoaded) {
            nursingProcessRepository.getNursingProcessList().onSuccess { processList ->
                cache.processes = processList
                cache.processesLoaded = true
            }
        }

        val subjectsToLoad =
            if (subjectFilter != null) subjectList.filter { it.id == subjectFilter } else subjectList

        val missingCaseStudySubjects = subjectsToLoad.filter {
            it.features?.contains(SubjectFeatures.CASE_STUDIES) == true && !cache.caseStudies.containsKey(it.id)
        }
        val missingDiagnosticSubjects = subjectsToLoad.filter {
            it.features?.contains(SubjectFeatures.NURSING_DIAGNOSTICS) == true && !cache.diagnostics.containsKey(it.id)
        }

        missingCaseStudySubjects.forEach { subject ->
            subjectRepository.getCaseStudiesBySubject(subject.id).onSuccess { list ->
                cache.caseStudies[subject.id] = list
            }
        }

        missingDiagnosticSubjects.forEach { subject ->
            subjectRepository.getNursingDiagnosticsBySubject(subject.id).onSuccess { list ->
                cache.diagnostics[subject.id] = list
            }
        }
    }

    private fun collectResults(
        normalizedQuery: String,
        filters: HomeSearchState.AppliedFilters,
        subjectLookup: Map<String, Subject>,
    ): List<HomeSearchState.SearchItem> {
        // Merge the results from each domain area and keep only the most relevant matches.
        val resultTypes = filters.types
        val subjectFilter = filters.subjectId

        val processResults =
            if (HomeSearchState.ResultType.PROCESS in resultTypes && subjectFilter == null) {
                cache.processes
                    .filter { process ->
                        process.title.contains(normalizedQuery, ignoreCase = true) ||
                            process.body.contains(normalizedQuery, ignoreCase = true)
                    }
                    .map { process ->
                        val snippet = process.body
                            .sanitizeLineBreaks()
                            .buildSnippet(normalizedQuery)
                        HomeSearchState.SearchItem(
                            id = process.remoteId,
                            title = process.title,
                            subtitle = null,
                            snippet = snippet,
                            type = HomeSearchState.ResultType.PROCESS,
                            subjectTitle = null,
                            subjectId = null,
                        )
                    }
            } else {
                emptyList()
            }

        val caseStudyResults =
            if (HomeSearchState.ResultType.CASE_STUDY in resultTypes) {
                cache.caseStudies
                    .filterForSubject(subjectFilter)
                    .flatMap { (subjectId, caseStudies) ->
                        caseStudies.filter { caseStudy ->
                            caseStudy.intro.contains(normalizedQuery, ignoreCase = true) ||
                                caseStudy.explanation.contains(normalizedQuery, ignoreCase = true)
                        }.map { caseStudy ->
                            val snippetSource =
                                when {
                                    caseStudy.intro.contains(normalizedQuery, ignoreCase = true) -> caseStudy.intro
                                    caseStudy.explanation.contains(normalizedQuery, ignoreCase = true) -> caseStudy.explanation
                                    else -> null
                                }
                            val sanitizedSnippet = snippetSource?.sanitizeLineBreaks()
                            HomeSearchState.SearchItem(
                                id = caseStudy.remoteId,
                                title = AppStrings.CaseStudyList.cardTitle(caseStudy.id),
                                subtitle = subjectLookup[subjectId]?.title,
                                snippet = sanitizedSnippet?.buildSnippet(normalizedQuery),
                                type = HomeSearchState.ResultType.CASE_STUDY,
                                subjectTitle = subjectLookup[subjectId]?.title,
                                subjectId = subjectId,
                            )
                        }
                    }
            } else {
                emptyList()
            }

        val diagnosticResults =
            if (HomeSearchState.ResultType.DIAGNOSTIC in resultTypes) {
                cache.diagnostics
                    .filterForSubject(subjectFilter)
                    .flatMap { (subjectId, diagnostics) ->
                        diagnostics.filter { diagnostic ->
                            diagnostic.title.contains(normalizedQuery, ignoreCase = true) ||
                                diagnostic.description.contains(normalizedQuery, ignoreCase = true)
                        }.map { diagnostic ->
                            val snippetSource =
                                when {
                                    diagnostic.description.contains(normalizedQuery, ignoreCase = true) -> diagnostic.description
                                    diagnostic.category.contains(normalizedQuery, ignoreCase = true) -> diagnostic.category
                                    else -> null
                                }
                            val sanitizedSnippet = snippetSource?.sanitizeLineBreaks()
                            HomeSearchState.SearchItem(
                                id = diagnostic.remoteId,
                                title = diagnostic.title,
                                subtitle = diagnostic.category,
                                snippet = sanitizedSnippet?.buildSnippet(normalizedQuery),
                                type = HomeSearchState.ResultType.DIAGNOSTIC,
                                subjectTitle = subjectLookup[subjectId]?.title,
                                subjectId = subjectId,
                            )
                        }
                    }
            } else {
                emptyList()
            }

        return (processResults + diagnosticResults + caseStudyResults)
            .sortedBy { it.title }
            .take(MAX_RESULTS)
    }

    private fun <T> Map<String, List<T>>.filterForSubject(subjectId: String?): Map<String, List<T>> {
        return if (subjectId == null) this else this.filterKeys { it == subjectId }
    }

    private class SearchCache {
        var processes: List<NursingProcess> = emptyList()
        var processesLoaded: Boolean = false
        val caseStudies: MutableMap<String, List<CaseStudy>> = mutableMapOf()
        val diagnostics: MutableMap<String, List<NursingDiagnostic>> = mutableMapOf()
        var requestedFilters: HomeSearchState.AppliedFilters = HomeSearchState.AppliedFilters()
    }

    private fun String.buildSnippet(normalizedQuery: String, radius: Int = 60): String {
        // Extract a small excerpt around the matched query so the user understands the context instantly.
        val index = this.indexOf(normalizedQuery, ignoreCase = true)
        if (index < 0) return this.take(radius).appendEllipsisIfLonger(this.length)

        val start = (index - radius).coerceAtLeast(0)
        val end = (index + normalizedQuery.length + radius).coerceAtMost(this.length)
        val prefixEllipsis = if (start > 0) "..." else ""
        val suffixEllipsis = if (end < this.length) "..." else ""
        return prefixEllipsis + this.substring(start, end).trim() + suffixEllipsis
    }

    private fun String.appendEllipsisIfLonger(originalLength: Int): String {
        return if (originalLength > this.length) "${this.trim()}..." else this
    }

    companion object {
        private const val MAX_RESULTS = 10
    }
}

private fun String.sanitizeLineBreaks(): String =
    this.replace('\n', ' ').replace('\r', ' ')
