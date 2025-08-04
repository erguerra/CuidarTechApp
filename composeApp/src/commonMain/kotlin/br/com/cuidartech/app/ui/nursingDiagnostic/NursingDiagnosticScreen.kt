package br.com.cuidartech.app.ui.nursingDiagnostic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingDiagnosticScreen(
    primaryColor: Long?,
    navigateBack: () -> Unit,
) {
    val viewModel: NursingDiagnosticViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NursingDiagnosticContent(
        viewState = state,
        navigateBack = navigateBack,
        primaryColor = primaryColor?.let { Color(it) },
    )
}
