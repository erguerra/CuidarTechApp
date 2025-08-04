package br.com.cuidartech.app.ui.nursingProcess

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NursingProcessScreen(navigateBack: () -> Unit) {
    val viewModel: NursingProcessViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    NursingProcessContent(
        navigateBack = navigateBack,
        onReferenceClick = viewModel::launchUrl,
        viewState = state,
    )

    EventHandler(
        viewModel = viewModel,
        launchUrl = uriHandler::openUri,
    )
}

@Composable
private fun EventHandler(
    viewModel: NursingProcessViewModel,
    launchUrl: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.event
            .onEach { event ->
                when (event) {
                    is NursingProcessViewModel.Event.LaunchReferenceUrl -> launchUrl(event.url)
                }
            }.launchIn(this)
    }
}
