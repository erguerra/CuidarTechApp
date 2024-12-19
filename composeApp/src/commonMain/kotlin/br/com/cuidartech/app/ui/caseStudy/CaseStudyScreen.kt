package br.com.cuidartech.app.ui.caseStudy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CaseStudyScreen(
    title: String,
    primaryColorLong: Long?,
    navigateBack: () -> Unit,
) {

    val viewModel: CaseStudyViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CaseStudyContent(
        title = title,
        primaryColor = primaryColorLong?.run {  Color(this) },
        viewState = state,
        navigateBack = navigateBack,
        chooseAlternative = viewModel::evaluateAnswer,
        closeModal = viewModel::closeDialog,
    )

}