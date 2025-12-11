package br.com.cuidartech.app.data

import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.domain.model.NursingProcess

class SearchDataCache {
    var processes: List<NursingProcess> = emptyList()
        private set
    var processesLoaded: Boolean = false
        private set
    val caseStudies: MutableMap<String, List<CaseStudy>> = mutableMapOf()
    val diagnostics: MutableMap<String, List<NursingDiagnostic>> = mutableMapOf()

    fun updateProcesses(list: List<NursingProcess>) {
        processes = list
        processesLoaded = true
    }

    fun cleanUp() {
        processes = emptyList()
        processesLoaded = false
        caseStudies.clear()
        diagnostics.clear()
    }
}
