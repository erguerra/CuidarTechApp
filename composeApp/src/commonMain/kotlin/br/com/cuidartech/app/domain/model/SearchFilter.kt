package br.com.cuidartech.app.domain.model

data class SearchFilter(
    val subjectId: String? = null,
    val types: Set<SearchResultType> = SearchResultType.entries.toSet(),
)
