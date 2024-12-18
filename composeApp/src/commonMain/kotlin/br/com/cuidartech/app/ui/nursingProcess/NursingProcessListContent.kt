package br.com.cuidartech.app.ui.nursingProcess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.domain.model.NursingProcess
import br.com.cuidartech.app.ui.components.Header
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_case_study
import cuidartechapp.composeapp.generated.resources.icon_nursing_process
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NursingProcessListContent(
    state: NursingProcessListViewModel.ViewState,
    onItemClick: (nursingProcess: NursingProcess) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is NursingProcessListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is NursingProcessListViewModel.ViewState.Error -> Text("QUe merda tá acontecendo????")
            is NursingProcessListViewModel.ViewState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Header(
                        title = "Processos de Enfermagem"
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }

                items(state.nursingProcessList) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium,
                        onClick = { onItemClick(it) }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    modifier = Modifier.size(42.dp),
                                    painter = painterResource(Res.drawable.icon_nursing_process),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = "Processo de Enfermagem",
                                )
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = it.body,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

