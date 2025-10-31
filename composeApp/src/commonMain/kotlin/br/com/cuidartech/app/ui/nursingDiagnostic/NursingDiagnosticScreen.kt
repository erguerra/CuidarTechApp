package br.com.cuidartech.app.ui.nursingDiagnostic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingDiagnosticScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: NursingDiagnosticViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NursingDiagnosticContent(
        viewState = state,
        navigateBack = navigateBack,
    )
}
