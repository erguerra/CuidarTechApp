package br.com.cuidartech.app.ui.splash

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cuidartech.app.domain.model.Subject
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.background_pattern
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    onFinished: (List<Subject>) -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is SplashViewModel.ViewState.Success) {
            onFinished((state as SplashViewModel.ViewState.Success).subjectList)
        }
    }

    SplashContent(
        state = state,
        onRetry = viewModel::retry,
    )
}

@Composable
private fun SplashContent(
    state: SplashViewModel.ViewState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Image(
            painter = painterResource(Res.drawable.background_pattern),
            contentDescription = null,
            modifier = Modifier.size(180.dp).align(Alignment.TopEnd).padding(top = 24.dp, end = 16.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)),
        )

        Image(
            painter = painterResource(Res.drawable.background_pattern),
            contentDescription = null,
            modifier = Modifier.size(180.dp).align(Alignment.BottomStart).padding(bottom = 24.dp, start = 16.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)),
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "CuidarTech",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Seu guia para o processo de enfermagem",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            )

            Spacer(modifier = Modifier.size(48.dp))

            Crossfade(targetState = state, label = "splash-state") { viewState ->
                when (viewState) {
                    SplashViewModel.ViewState.Loading ->
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)

                    SplashViewModel.ViewState.Error ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Text(
                                text = "Não conseguimos carregar os conteúdos.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Button(onClick = onRetry) {
                                Text(text = "Tentar novamente")
                            }
                        }

                    is SplashViewModel.ViewState.Success ->
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
