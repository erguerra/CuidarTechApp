package br.com.cuidartech.app.ui.caseStudy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    )

    EventHandler(
        viewModel = viewModel,
        navigateBack = navigateBack,
    )
}

@Composable
private fun EventHandler(
    viewModel: CaseStudyViewModel,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.event.onEach { event ->
            when(event) {
                is CaseStudyViewModel.Event.ShowRightAnswerFeedback -> navigateBack()
                is CaseStudyViewModel.Event.ShowWrongAnswerFeedback -> {}
            }
        }.launchIn(this)
    }
}