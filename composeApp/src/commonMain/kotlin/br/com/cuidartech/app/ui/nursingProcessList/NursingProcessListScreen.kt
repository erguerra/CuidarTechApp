package br.com.cuidartech.app.ui.nursingProcessList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingProcessListScreen(
    viewModel: NursingProcessListViewModel = koinViewModel(),
    navigateToItem: (processId: String) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.getNursingList()
    }

    NursingProcessListContent(
        state = state,
        onItemClick = navigateToItem,
        navigateBack = navigateBack,
    )
}
