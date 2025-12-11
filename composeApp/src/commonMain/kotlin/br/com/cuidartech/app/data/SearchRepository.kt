package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.domain.model.SubjectFeatures

/**
 * Facade that coordinates all data fetching needed by the search feature.
 * It hides caching + repository orchestration from the domain layer.
 */
class SearchRepository(
    private val subjectRepository: SubjectRepository,
    private val nursingProcessRepository: NursingProcessRepository,
    private val cache: SearchDataCache,
) {
    suspend fun ensureBaseDataLoaded(subjectFilter: String?, subjects: List<Subject>) {
        ensureProcessesLoaded()

        val subjectsToLoad =
            if (subjectFilter != null) subjects.filter { it.id == subjectFilter } else subjects

        ensureCaseStudiesLoaded(subjectsToLoad)
        ensureDiagnosticsLoaded(subjectsToLoad)
    }

    fun getProcesses() = cache.processes

    fun getCaseStudiesBySubject() = cache.caseStudies

    fun getDiagnosticsBySubject() = cache.diagnostics

    fun clearCache() {
        cache.cleanUp()
    }

    private suspend fun ensureProcessesLoaded() {
        if (!cache.processesLoaded) {
            val processList = nursingProcessRepository.getNursingProcessList().getOrThrow()
            cache.updateProcesses(processList)
        }
    }

    private suspend fun ensureCaseStudiesLoaded(subjects: List<Subject>) {
        val missingCaseStudySubjects = subjects.filter {
            it.features?.contains(SubjectFeatures.CASE_STUDIES) == true && !cache.caseStudies.containsKey(it.id)
        }

        missingCaseStudySubjects.forEach { subject ->
            val caseStudies = subjectRepository.getCaseStudiesBySubject(subject.id).getOrThrow()
            cache.caseStudies[subject.id] = caseStudies
        }
    }

    private suspend fun ensureDiagnosticsLoaded(subjects: List<Subject>) {
        val missingDiagnosticSubjects = subjects.filter {
            it.features?.contains(SubjectFeatures.NURSING_DIAGNOSTICS) == true && !cache.diagnostics.containsKey(it.id)
        }

        missingDiagnosticSubjects.forEach { subject ->
            val diagnostics = subjectRepository.getNursingDiagnosticsBySubject(subject.id).getOrThrow()
            cache.diagnostics[subject.id] = diagnostics
        }
    }
}
