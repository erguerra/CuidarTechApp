package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.domain.model.NursingProcess

/**
 * Keeps the in-memory snapshot of all data necessary for searches.
 * ViewModels talk to the use case, which talks to the repository, which writes here.
 */
class SearchDataCache {
    var processes: List<NursingProcess> = emptyList()
        private set
    var processesLoaded: Boolean = false
        private set
    val caseStudies: MutableMap<String, List<CaseStudy>> = mutableMapOf()
    val diagnostics: MutableMap<String, List<NursingDiagnostic>> = mutableMapOf()

    /** Store the full list of processes so future searches can skip hitting the network. */
    fun updateProcesses(list: List<NursingProcess>) {
        processes = list
        processesLoaded = true
    }

    /** Forget everything so the next search starts fresh (useful for logout/refresh scenarios). */
    fun cleanUp() {
        processes = emptyList()
        processesLoaded = false
        caseStudies.clear()
        diagnostics.clear()
    }
}
