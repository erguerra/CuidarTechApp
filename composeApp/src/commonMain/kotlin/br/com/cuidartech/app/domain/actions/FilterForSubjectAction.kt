package br.com.cuidartech.app.domain.actions

class FilterForSubjectAction {

    operator fun <T> invoke(
        subjectId: String?,
        entityBySubject: Map<String, List<T>>,
    ) : Map<String, List<T>>{
        return if (subjectId == null) {
            entityBySubject
        } else {
            entityBySubject.filterKeys { it == subjectId }
        }
    }
}