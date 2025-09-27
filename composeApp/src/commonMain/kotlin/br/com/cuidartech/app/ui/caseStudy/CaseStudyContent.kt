package br.com.cuidartech.app.ui.caseStudy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.domain.model.Alternative
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.LargeText
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_case_study
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CaseStudyContent(
    title: String,
    primaryColor: Color?,
    viewState: CaseStudyViewModel.ViewState,
    chooseAlternative: (Alternative) -> Unit,
    closeModal: () -> Unit,
    toggleScenario: () -> Unit,
    navigateBack: () -> Unit,
) {
    val customColor = primaryColor ?: MaterialTheme.colorScheme.primary
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = title,
                contentColor = primaryColor,
                navigateBackAction = navigateBack,
            )
        },
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when {
                viewState.error != null -> viewState.error.message?.let { Text(it) }
                viewState.isLoading ->
                    CircularProgressIndicator(
                        color = customColor,
                    )

                !viewState.isLoading && viewState.caseStudy != null -> {
                    val stateVertical = rememberScrollState(0)
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(
                                    stateVertical,
                                ).padding(16.dp),
                    ) {
                        val interactionSource = remember { MutableInteractionSource() }
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null,
                                        onClick = toggleScenario,
                                    ).animateContentSize(),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.size(42.dp),
                                    painter = painterResource(Res.drawable.icon_case_study),
                                    tint = customColor,
                                    contentDescription = "Estudo de Caso",
                                )
                                Text(
                                    text = "Cenário",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Row(
                                    modifier = Modifier.weight(1f),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    RotatingArrow(viewState.isScenarioExpanded)
                                }
                            }
                            Spacer(Modifier.size(16.dp))

                            AnimatedVisibility(
                                visible = viewState.isScenarioExpanded,
                                enter = fadeIn(tween(500)),
                                exit = fadeOut(tween(500)),
                            ) {
                                Spacer(Modifier.size(8.dp))
                                LargeText(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp),
                                    text = viewState.caseStudy.intro,
                                )

                                Spacer(Modifier.size(16.dp))
                            }
                        }

//                        }
                        Spacer(Modifier.size(24.dp))

                        val question =
                            viewState.caseStudy.question ?: "Escolha uma das alternativas abaixo"

                        Text(
                            text = question,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = customColor,
                        )
                        Spacer(Modifier.size(24.dp))

                        viewState.caseStudy.options.forEach { alternative ->
                            Alternative(
                                text = alternative.content,
                                primaryColor = customColor,
                                onClick = {
                                    chooseAlternative(alternative)
                                },
                            )

                            Spacer(Modifier.size(8.dp))
                        }
                    }
                }
            }

            if (viewState.showDialog) {
                FeedbackDialog(
                    variant = viewState.dialogVariant,
                    content = viewState.dialogMessage.orEmpty(),
                    primaryColor = customColor,
                    close = closeModal,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Alternative(
    text: String,
    primaryColor: Color,
    onClick: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        border =
            BorderStroke(
                1.dp,
                primaryColor,
            ),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        elevation = (-2).dp,
    ) {
        Box(
            modifier =
                Modifier.fillMaxWidth().height(64.dp).padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun RotatingArrow(isExpanded: Boolean) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        animationSpec = tween(durationMillis = 300),
    )

    Icon(
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // points right
        modifier = Modifier.size(36.dp).rotate(rotationAngle),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "Expandir",
    )
}
