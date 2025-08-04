package br.com.cuidartech.app.ui.nursingDiagnostic

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
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_intervention
import cuidartechapp.composeapp.generated.resources.icon_nursing_diagnostic
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingDiagnosticContent(
    viewState: NursingDiagnosticViewModel.ViewState,
    primaryColor: Color?,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = "Diagnósticos",
                contentColor = primaryColor,
                navigateBackAction = navigateBack,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                is NursingDiagnosticViewModel.ViewState.Error -> Text("Deu merda!")
                is NursingDiagnosticViewModel.ViewState.Loading ->
                    CircularProgressIndicator(
                        color = primaryColor ?: MaterialTheme.colorScheme.primary,
                    )

                is NursingDiagnosticViewModel.ViewState.Success -> {
                    val stateVertical = rememberScrollState(0)
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .verticalScroll(
                                    stateVertical,
                                ).padding(16.dp),
                    ) {
                        primaryColor?.copy(alpha = 0.08f)?.let { safePrimaryColor ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.large,
                                color = safePrimaryColor,
                                border = BorderStroke(2.dp, primaryColor),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(42.dp),
                                            painter = painterResource(Res.drawable.icon_nursing_diagnostic),
                                            tint = primaryColor,
                                            contentDescription = "Diagnóstico",
                                        )
                                        Text(
                                            text = viewState.nursingDiagnostic.title,
                                            style = MaterialTheme.typography.headlineLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = primaryColor,
                                        )
                                    }
                                    Spacer(Modifier.size(16.dp))
                                    Text(
                                        text = "Categoria: ${viewState.nursingDiagnostic.category}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = primaryColor,
                                    )

                                    Spacer(Modifier.size(24.dp))
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.DarkGray,
                                        text = viewState.nursingDiagnostic.description,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                    )

                                    Spacer(Modifier.size(16.dp))
                                }
                            }
                        }
                        Spacer(Modifier.size(24.dp))

                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium,
                            border =
                                BorderStroke(
                                    1.dp,
                                    (
                                        primaryColor
                                            ?: MaterialTheme.colorScheme.primary
                                    ).copy(alpha = 0.5f),
                                ),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Intervenções",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = primaryColor ?: MaterialTheme.colorScheme.primary,
                                    )
                                    Icon(
                                        modifier = Modifier.size(42.dp),
                                        painter = painterResource(Res.drawable.icon_intervention),
                                        tint = primaryColor ?: MaterialTheme.colorScheme.primary,
                                        contentDescription = "Intervenção",
                                    )
                                }

                                Spacer(Modifier.size(24.dp))

                                viewState.nursingDiagnostic.interventions.forEach { intervention ->

                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp).alignByBaseline(),
                                            painter = painterResource(Res.drawable.icon_intervention),
                                            tint =
                                                primaryColor
                                                    ?: MaterialTheme.colorScheme.primary,
                                            contentDescription = "Intervenção",
                                        )
                                        Text(
                                            text = intervention,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp,
                                            color = Color.DarkGray,
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
}
