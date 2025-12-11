package br.com.cuidartech.app.domain.actions

import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.domain.model.SearchResult
import br.com.cuidartech.app.domain.model.SearchResultType
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.ktx.sanitizeLineBreaks
import kotlin.collections.component1
import kotlin.text.contains

class SearchDiagnosticAction(
    private val buildSnippet: BuildSearchSnippetAction,
    private val filterForSubject: FilterForSubjectAction,
) {
    suspend operator fun invoke(
        query: String,
        subjectId: String?,
        diagnosticsBySubject: Map<String, List<NursingDiagnostic>>,
        subjectLookup: Map<String, Subject>,
    ): List<SearchResult> {
        val filteredBySubject = filterForSubject(subjectId, diagnosticsBySubject)
        return filteredBySubject.flatMap { (subjectId, diagnostics) ->
            diagnostics.filter { diagnostic ->
                diagnostic.title.contains(query, ignoreCase = true) ||
                        diagnostic.description.contains(query, ignoreCase = true)
            }.map { diagnostic ->
                val snippetSource = when {
                    diagnostic.description.contains(
                        query,
                        ignoreCase = true
                    ) -> diagnostic.description

                    diagnostic.category.contains(query, ignoreCase = true) -> diagnostic.category
                    else -> null
                }
                SearchResult(
                    id = diagnostic.remoteId,
                    title = diagnostic.title,
                    subtitle = diagnostic.category,
                    snippet = buildSnippet(snippetSource?.sanitizeLineBreaks(), query),
                    type = SearchResultType.DIAGNOSTIC,
                    subjectTitle = subjectLookup[subjectId]?.title,
                    subjectId = subjectId
                )

            }

        }

    }
}