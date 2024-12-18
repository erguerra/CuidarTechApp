package br.com.cuidartech.app.app

import br.com.cuidartech.app.domain.model.NursingProcess
import br.com.cuidartech.app.ui.model.SubjectUIModel
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CuidarTechGraphRoute: Route

    @Serializable
    data object HomeRoute: Route

    @Serializable
    data object NursingProcessListRoute: Route

    @Serializable
    data class NursingProcessRoute(val nursingProcess: NursingProcess): Route

    @Serializable
    data class CaseStudyListRoute(val title: String, val subjectId: String): Route

    @Serializable
    data class DiagnosticListRoute(val subjectId: String): Route

    @Serializable
    data class CaseStudyRoute(val caseStudyPath: String): Route

    @Serializable
    data class DiagnosticRoute(val diagnosticPath: String): Route
}