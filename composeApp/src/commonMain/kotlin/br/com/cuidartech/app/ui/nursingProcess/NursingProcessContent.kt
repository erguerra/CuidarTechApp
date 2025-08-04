package br.com.cuidartech.app.ui.nursingProcess

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.LargeText
import br.com.cuidartech.app.ui.theme.CuidardTechTheme
import br.com.cuidartech.app.ui.theme.getMerriweatherSans
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_file_open
import cuidartechapp.composeapp.generated.resources.icon_nursing_process
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingProcessContent(
    viewState: NursingProcessViewModel.ViewState,
    navigateBack: () -> Unit,
    onReferenceClick: (url: String) -> Unit,
) {
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = "Processo de Enfermagem",
                contentColor = MaterialTheme.colorScheme.primary,
                navigateBackAction = navigateBack,
            )
        },
    ) { paddingValues ->

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                is NursingProcessViewModel.ViewState.Error -> Text("Deu merda!")
                is NursingProcessViewModel.ViewState.Loading ->
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )

                is NursingProcessViewModel.ViewState.Success -> {
                    var textVisibility by remember {
                        mutableStateOf(true)
                    }
                    val stateVertical = rememberScrollState(0)
                    val density = LocalDensity.current
                    AnimatedVisibility(
                        textVisibility,
                        enter =
                            slideInVertically {
                                // Slide in from 40 dp from the top.
                                with(density) { -40.dp.roundToPx() }
                            } +
                                expandVertically(
                                    // Expand from the top.
                                    expandFrom = Alignment.Top,
                                ) +
                                fadeIn(
                                    // Fade in with the initial alpha of 0.3f.
                                    initialAlpha = 0.3f,
                                ),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .verticalScroll(
                                        stateVertical,
                                    ).padding(16.dp),
                        ) {
                            Column(
                                modifier =
                                    Modifier.fillMaxWidth().padding(
                                        PaddingValues(start = 16.dp, end = 8.dp, bottom = 16.dp),
                                    ),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        modifier = Modifier.size(42.dp),
                                        painter = painterResource(Res.drawable.icon_nursing_process),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = "Processo de Enfermagem",
                                    )
                                    Text(
                                        text = viewState.nursingProcess.title,
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }
                                Spacer(Modifier.size(24.dp))

                                LargeText(text = viewState.nursingProcess.body)
                            }
                            viewState.nursingProcess.references.forEach {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if (it.url != null) {
                                        IconButton(
                                            onClick = {
                                                onReferenceClick(it.url)
                                            },
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(20.dp).alignByBaseline(),
                                                painter = painterResource(Res.drawable.icon_file_open),
                                                tint = MaterialTheme.colorScheme.primary,
                                                contentDescription = "Abrir referência",
                                            )
                                        }
                                    }
                                    Text(
                                        text = it.reference,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp,
                                        color = Color.DarkGray,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
