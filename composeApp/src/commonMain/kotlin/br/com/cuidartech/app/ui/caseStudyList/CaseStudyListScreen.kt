package br.com.cuidartech.app.ui.caseStudyList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cuidartech.app.ui.model.CaseStudyItemUiModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CaseStudyListScreen(
    navigateToAnItem: (studyCase: CaseStudyItemUiModel) -> Unit,
    navigateBack: () -> Unit,
) {

    val viewModel: CaseStudyListViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CaseStudyListContent(
        navigateBack = navigateBack,
        viewState = state,
        onItemClick = navigateToAnItem,
    )
}