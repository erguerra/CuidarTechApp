package br.com.cuidartech.app.domain.actions

import br.com.cuidartech.app.domain.model.NursingProcess
import br.com.cuidartech.app.domain.model.SearchResult
import br.com.cuidartech.app.domain.model.SearchResultType
import br.com.cuidartech.app.ktx.sanitizeLineBreaks

class SearchProcessesAction(
    private val buildSnippet: BuildSearchSnippetAction,
) {

    operator fun invoke(
        query: String,
        processes: List<NursingProcess>,
    ): List<SearchResult> {
        return if(processes.isEmpty()) {
            emptyList()
        } else {
            processes
                .filter { process ->
                    process.title.contains(query, ignoreCase = true) ||
                            process.body.contains(query, ignoreCase = true)
                }
                .map { process ->
                    val normalizedProcessBody = process.body
                        .sanitizeLineBreaks()
                    val snippet = buildSnippet(normalizedProcessBody, query)
                    SearchResult(
                        id = process.remoteId,
                        title = process.title,
                        subtitle = null,
                        snippet = snippet,
                        type = SearchResultType.PROCESS,
                        subjectTitle = null,
                        subjectId = null,
                    )
                }
        }

    }
}