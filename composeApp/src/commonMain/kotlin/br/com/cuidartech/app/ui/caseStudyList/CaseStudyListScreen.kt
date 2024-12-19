package br.com.cuidartech.app.ui.caseStudyList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cuidartech.app.domain.model.CaseStudy
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CaseStudyListScreen(
    title: String,
    primaryColorLong: Long?,
    navigateToAnItem: (studyCaseId: String, title: String, primaryColor: Long?) -> Unit,
    navigateBack: () -> Unit,
) {

    val viewModel: CaseStudyListViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CaseStudyListContent(
        title = title,
        primaryColor = primaryColorLong?.run { Color(this) },
        navigateBack = navigateBack,
        viewState = state,
        onItemClick = navigateToAnItem,
    )
}