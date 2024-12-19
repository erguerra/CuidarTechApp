package br.com.cuidartech.app.ui.caseStudy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.domain.model.Alternative
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
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
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = title,
                contentColor = primaryColor,
                navigateBackAction = navigateBack,
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                is CaseStudyViewModel.ViewState.Error -> Text("Deu merda!")
                is CaseStudyViewModel.ViewState.Loading -> CircularProgressIndicator(
                    color = primaryColor ?: MaterialTheme.colorScheme.primary,
                )

                is CaseStudyViewModel.ViewState.Success -> {
                    val stateVertical = rememberScrollState(0)
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(
                            stateVertical
                        ).padding(16.dp),
                    ) {
                        primaryColor?.copy(alpha = 0.08f)?.let {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.large,
                                color = it,
                                border = BorderStroke(2.dp, primaryColor)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(42.dp),
                                            painter = painterResource(Res.drawable.icon_case_study),
                                            tint = primaryColor,
                                            contentDescription = "Estudo de Caso",
                                        )
                                        Text(
                                            text = "Cenário",
                                            style = MaterialTheme.typography.headlineLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = primaryColor,
                                        )
                                    }
                                    Spacer(Modifier.size(24.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.DarkGray,
                                        text = viewState.caseStudy.intro,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium
                                    )

                                    Spacer(Modifier.size(16.dp))
                                }

                            }
                        }
                        Spacer(Modifier.size(24.dp))

                        viewState.caseStudy.question?.let { question ->
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = question,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = primaryColor ?: MaterialTheme.colorScheme.primary,

                                )
                            Spacer(Modifier.size(24.dp))
                        }

                        viewState.caseStudy.options.forEach { alternative ->
                            Surface(
                                color = primaryColor ?: MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.medium,
                                onClick = { chooseAlternative(alternative) }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp,
                                    ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = alternative.content,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }

                            Spacer(Modifier.size(8.dp))
                        }
                    }
                }
            }
        }

    }
}