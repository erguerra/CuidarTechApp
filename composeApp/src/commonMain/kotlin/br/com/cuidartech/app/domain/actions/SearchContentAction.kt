package br.com.cuidartech.app.domain.actions

import br.com.cuidartech.app.data.SearchRepository
import br.com.cuidartech.app.domain.model.SearchFilter
import br.com.cuidartech.app.domain.model.SearchResult
import br.com.cuidartech.app.domain.model.SearchResultType
import br.com.cuidartech.app.domain.model.Subject

class SearchContentAction(
    private val searchRepository: SearchRepository,
    private val searchProcesses: SearchProcessesAction,
    private val searchDiagnostics: SearchDiagnosticAction,
    private val searchCaseStudies: SearchCaseStudyAction,
) {

    private val processes by lazy { searchRepository.getProcesses() }
    private val caseStudiesBySubject by lazy { searchRepository.getCaseStudiesBySubject()}
    private val diagnosticsBySubject by lazy { searchRepository.getDiagnosticsBySubject()}

    suspend operator fun invoke(
        query: String,
        filters: SearchFilter,
        subjects: List<Subject>,
    ): Result<List<SearchResult>> =
        runCatching {
            searchRepository.ensureBaseDataLoaded(filters.subjectId, subjects)
            val subjectLookup = subjects.associateBy { it.id }
            buildResults(
                query = query,
                filters = filters,
                subjectLookup = subjectLookup,
            )
        }

    private fun buildResults(
        query: String,
        filters: SearchFilter,
        subjectLookup: Map<String, Subject>,
    ): List<SearchResult> {
        val normalizedQuery = query.trim().lowercase()
        if (normalizedQuery.isEmpty()) return emptyList()

        val resultTypes = filters.types
        val subjectFilter = filters.subjectId

        val processResults = if (shouldLookForProcesses(filters)) {
            searchProcesses(normalizedQuery, processes)
        } else {
            emptyList()
        }

        val caseStudyResults =
            if (SearchResultType.CASE_STUDY in resultTypes) {
                searchCaseStudies(normalizedQuery, subjectFilter, caseStudiesBySubject, subjectLookup)
            } else {
                emptyList()
            }

        val diagnosticResults =
            if (SearchResultType.DIAGNOSTIC in resultTypes) {
                searchDiagnostics(normalizedQuery, subjectFilter, diagnosticsBySubject, subjectLookup)
            } else {
                emptyList()
            }

        return (diagnosticResults + processResults + caseStudyResults).take(MAX_RESULTS)
    }

    private fun shouldLookForProcesses(filters: SearchFilter) : Boolean {
        return filters.types.contains(SearchResultType.PROCESS)
                && filters.subjectId == null
    }

    companion object {
        private const val MAX_RESULTS = 10
    }
}
