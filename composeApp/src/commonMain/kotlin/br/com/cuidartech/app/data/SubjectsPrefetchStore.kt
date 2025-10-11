package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SubjectsPrefetchStore {
    private val preloadedSubjects = MutableStateFlow<List<Subject>?>(null)

    fun store(subjects: List<Subject>) {
        preloadedSubjects.value = subjects
    }

    fun consume(): List<Subject>? =
        preloadedSubjects.value.also {
            if (it != null) {
                preloadedSubjects.update { null }
            }
        }
}
