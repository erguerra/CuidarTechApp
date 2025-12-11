package br.com.cuidartech.app.ui.subjects

sealed interface HomeSearchState {
    data object Idle : HomeSearchState
    data object Loading : HomeSearchState
    data class Results(
        val query: String,
        val items: List<SearchItem>,
        val appliedFilters: AppliedFilters,
    ) : HomeSearchState
    data object Error : HomeSearchState

    data class SearchItem(
        val id: String,
        val title: String?,
        val subtitle: String?,
        val snippet: String?,
        val type: ResultType,
        val subjectTitle: String?,
        val subjectId: String?,
    )

    data class AppliedFilters(
        val subjectId: String? = null,
        val types: Set<ResultType> = setOf(ResultType.PROCESS, ResultType.DIAGNOSTIC, ResultType.CASE_STUDY),
    )

    enum class ResultType { PROCESS, DIAGNOSTIC, CASE_STUDY }
}
