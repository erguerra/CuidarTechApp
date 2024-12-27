package br.com.cuidartech.app.ui.model

data class NursingDiagnosticItemUiModel(
    val id: Int,
    val remoteId: String,
    override val title: String,
    val category: String,
) : NursingDiagnosticListItem