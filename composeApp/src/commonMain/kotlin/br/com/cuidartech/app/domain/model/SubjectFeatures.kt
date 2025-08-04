package br.com.cuidartech.app.domain.model

enum class SubjectFeatures(
    val serializedName: String,
    val title: String,
) {
    CASE_STUDIES("case_studies", "Estudos de Caso"),
    NURSING_DIAGNOSTICS("nursing_diagnostics", "Diagnósticos"),
}
