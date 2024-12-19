package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingDiagnosticListScreen(
    title: String,
    primaryColorLong: Long?,
    navigateToAnItem: (diagnosticPath: String, primaryColor: Long?) -> Unit,
    navigateBack: () -> Unit,
) {

    val viewModel: NursingDiagnosticListViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NursingDiagnosticListContent(
        title = title,
        primaryColor = primaryColorLong?.run { Color(this) },
        navigateBack = navigateBack,
        onItemClick = navigateToAnItem,
        viewState = state,
    )

    LaunchedEffect(Unit) {
        viewModel.getDiagnosticList()
    }
}