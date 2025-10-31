package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingDiagnosticListScreen(
    title: String,
    navigateToAnItem: (diagnosticPath: String) -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: NursingDiagnosticListViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery = (state as? NursingDiagnosticListViewModel.ViewState.Success)?.query.orEmpty()

    NursingDiagnosticListContent(
        title = title,
        navigateBack = navigateBack,
        onItemClick = navigateToAnItem,
        viewState = state,
        searchQuery = searchQuery,
        onSearchQueryChange = viewModel::onQueryChange,
    )

    LaunchedEffect(Unit) {
        viewModel.getDiagnosticList()
    }
}
