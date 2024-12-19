package br.com.cuidartech.app.ui.model

import br.com.cuidartech.app.domain.model.SubjectFeatures
import kotlinx.serialization.Serializable

@Serializable
data class SubjectUIModel(
    val id: String,
    val title: String,
    val backgroundColor: Long,
    val features: Set<SubjectFeatures>?,
    val goToCaseStudies: (subjectId: String, title:String, primaryColorLong: Long?) -> Unit,
    val goToNursingDiagnostics: (subjectId: String, title: String, primaryColor: Long?) -> Unit,
)