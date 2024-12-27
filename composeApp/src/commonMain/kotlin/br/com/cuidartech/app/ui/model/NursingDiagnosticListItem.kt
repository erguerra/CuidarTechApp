package br.com.cuidartech.app.ui.model

interface NursingDiagnosticListItem {
    val title: String
}

data class NursingDiagnosticCategoryDivider(
    override val title: String,
) : NursingDiagnosticListItem