package br.com.cuidartech.app.app.navigation

import br.com.cuidartech.app.domain.model.NursingProcess
import br.com.cuidartech.app.domain.model.Subject
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object CuidarTechGraphRoute : Route

    @Serializable
    data object HomeRoute : Route

    @Serializable
    data object NursingProcessListRoute : Route

    @Serializable
    data class NursingProcessRoute(
        val nursingProcessId: String,
    ) : Route

    @Serializable
    data class CaseStudyListRoute(
        val subjectId: String,
        val title: String,
        val primaryColorLong: Long?,
    ) : Route

    @Serializable
    data class DiagnosticListRoute(
        val subjectId: String,
        val title: String,
        val primaryColorLong: Long?,
    ) : Route

    @Serializable
    data class CaseStudyRoute(
        val caseStudyPath: String,
        val title: String,
        val primaryColorLong: Long?,
    ) : Route

    @Serializable
    data class DiagnosticRoute(
        val diagnosticPath: String,
        val primaryColorLong: Long?,
    ) : Route
}
