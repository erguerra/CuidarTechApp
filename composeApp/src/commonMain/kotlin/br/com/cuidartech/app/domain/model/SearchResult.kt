package br.com.cuidartech.app.domain.model

data class SearchResult(
    val id: String,
    val title: String?,
    val subtitle: String?,
    val snippet: String?,
    val type: SearchResultType,
    val subjectTitle: String?,
    val subjectId: String?,
)
