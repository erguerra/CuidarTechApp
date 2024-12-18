package br.com.cuidartech.app.ui.subjects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    goToDiagnosticList: (subjectId: String) -> Unit,
    goToCaseStudyList: (title: String, subjectId: String) -> Unit,
    goToNursingProcessList: () -> Unit,
) {

    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    HomeContent(
        viewState = state,
        goToCaseStudyList = goToCaseStudyList,
        goToNursingProcessList = goToNursingProcessList,
        goToNursingDiagnosticList = goToDiagnosticList,
    )
}