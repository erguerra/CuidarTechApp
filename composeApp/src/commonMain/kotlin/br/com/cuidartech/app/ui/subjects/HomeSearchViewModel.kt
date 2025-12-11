package br.com.cuidartech.app.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cuidartech.app.domain.actions.SearchContentAction
import br.com.cuidartech.app.domain.model.SearchFilter
import br.com.cuidartech.app.domain.model.SearchResult
import br.com.cuidartech.app.domain.model.SearchResultType
import br.com.cuidartech.app.domain.model.Subject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeSearchViewModel(
    private val loadSearchResults: SearchContentAction,
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
    private var latestFilters: HomeSearchState.AppliedFilters = HomeSearchState.AppliedFilters()

    fun setSubjects(subjectList: List<Subject>) {
        // Cache the subjects so the sheet can build subject filters without re-fetching.
        _subjects.value = subjectList
    }

    fun onQueryChange(query: String, types: Set<HomeSearchState.ResultType>, subjectId: String?) {
        val filters = HomeSearchState.AppliedFilters(subjectId = subjectId, types = types)
        latestFilters = filters
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
        latestFilters = filters
        _filters.value = filters
        if (query.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            executeSearch(query)
        }
    }

    private suspend fun executeSearch(query: String) {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) {
            _searchState.value = HomeSearchState.Idle
            return
        }
        _searchState.value = HomeSearchState.Loading
        val filters = latestFilters
        val subjects = _subjects.value

        val result = loadSearchResults(
            query = normalizedQuery,
            filters = filters.toDomainFilters(),
            subjects = subjects,
        )

        result
            .onSuccess { list ->
                val items = list.map { it.toUiItem() }
                _searchState.value = HomeSearchState.Results(
                    query = normalizedQuery,
                    items = items,
                    appliedFilters = filters,
                )
            }
            .onFailure {
                _searchState.value = HomeSearchState.Error
            }
    }
}

private fun HomeSearchState.AppliedFilters.toDomainFilters(): SearchFilter {
    return SearchFilter(
        subjectId = subjectId,
        types = types.map { it.toDomainType() }.toSet(),
    )
}

private fun HomeSearchState.ResultType.toDomainType(): SearchResultType =
    when (this) {
        HomeSearchState.ResultType.PROCESS -> SearchResultType.PROCESS
        HomeSearchState.ResultType.DIAGNOSTIC -> SearchResultType.DIAGNOSTIC
        HomeSearchState.ResultType.CASE_STUDY -> SearchResultType.CASE_STUDY
    }

private fun SearchResult.toUiItem(): HomeSearchState.SearchItem {
        return HomeSearchState.SearchItem(
            id = id,
            title = title.orEmpty(),
            subtitle = subjectTitle,
            snippet = snippet,
            type = type.toUiType(),
            subjectTitle = subjectTitle,
            subjectId = subjectId,
        )
}

private fun SearchResultType.toUiType(): HomeSearchState.ResultType =
    when (this) {
        SearchResultType.PROCESS -> HomeSearchState.ResultType.PROCESS
        SearchResultType.DIAGNOSTIC -> HomeSearchState.ResultType.DIAGNOSTIC
        SearchResultType.CASE_STUDY -> HomeSearchState.ResultType.CASE_STUDY
    }
