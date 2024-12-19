package br.com.cuidartech.app.ui.nursingProcess

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
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
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                is NursingProcessViewModel.ViewState.Error -> Text("Deu merda!")
                is NursingProcessViewModel.ViewState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )

                is NursingProcessViewModel.ViewState.Success -> {
                    val stateVertical = rememberScrollState(0)
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(
                            stateVertical
                        ).padding(16.dp),
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(
                                    PaddingValues(start = 16.dp, end = 8.dp, bottom = 16.dp))
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
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                                Spacer(Modifier.size(24.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.DarkGray,
                                    text = viewState.nursingProcess.body,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        viewState.nursingProcess.references.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                if(it.url != null) {
                                    IconButton(
                                        onClick ={
                                            onReferenceClick(it.url)
                                        }
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