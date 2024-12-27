package br.com.cuidartech.app.domain.model

import br.com.cuidartech.app.ui.model.NursingDiagnosticListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Subject(
    val id: String,
    val title: String,
    @SerialName("primary_color")
    val primaryColor: Long?,
    val features: Set<SubjectFeatures>?,
)

@Serializable
data class CaseStudy(
    val remoteId: String,
    val id: Int,
    val intro: String,
    val question: String?,
    val explanation: String,
    val helper: String,
    val options: List<Alternative>
)

@Serializable
data class Alternative(
    val content: String,
    val isCorrect: Boolean,
)

@Serializable
data class NursingDiagnostic(
    val id: Int,
    val remoteId: String,
    val title: String,
    val description: String,
    val category: String,
    @SerialName("actions")
    val interventions: List<String>,
)
