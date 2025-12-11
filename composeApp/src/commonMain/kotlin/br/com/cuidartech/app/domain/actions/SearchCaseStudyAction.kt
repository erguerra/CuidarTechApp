package br.com.cuidartech.app.domain.actions

import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.SearchResult
import br.com.cuidartech.app.domain.model.SearchResultType
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.ktx.sanitizeLineBreaks
import dev.gitlive.firebase.firestore.Query

class SearchCaseStudyAction(
    private val buildSnippet: BuildSearchSnippetAction,
    private val filterForSubject: FilterForSubjectAction,
) {

    operator fun invoke(
        query: String,
        subjectId: String?,
        caseStudiesBySubject: Map<String, List<CaseStudy>>,
        subjectLookup: Map<String, Subject>,
    ): List<SearchResult> {
        val filteredCaseStudiesBySubject = filterForSubject(
            subjectId,
            caseStudiesBySubject,
        )
        return filteredCaseStudiesBySubject.flatMap { (subjectId, caseStudies) ->
            caseStudies.filter { caseStudy ->
                caseStudy.intro.contains(query, ignoreCase = true)
            }.map { caseStudy ->
                SearchResult(
                    id = caseStudy.remoteId,
                    title = null,
                    subtitle = subjectLookup[subjectId]?.title,
                    snippet = buildSnippet(caseStudy.intro, query),
                    type = SearchResultType.CASE_STUDY,
                    subjectTitle = subjectLookup[subjectId]?.title,
                    subjectId = subjectId,
                )
            }

        }
    }
}