package br.com.cuidartech.app.ui.subjects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.ui.components.Header
import br.com.cuidartech.app.ui.model.SubjectUIModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewState: HomeViewModel.ViewState,
    goToNursingProcessList: () -> Unit,
    goToCaseStudyList: (subjectId: String, title: String, primaryColorLong: Long?) -> Unit,
    goToNursingDiagnosticList: (subjectId: String, title: String, primaryColor: Long?) -> Unit,
) {
    Scaffold {

    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (viewState) {
            is HomeViewModel.ViewState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
            )
            is HomeViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is HomeViewModel.ViewState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Header(
                        title = "Bem Vindo(a)",
                    )
                    Spacer(Modifier.size(16.dp))
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                        backgroundColor = MaterialTheme.colorScheme.primary, // TODO: Reuse this color
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable {
                                goToNursingProcessList()
                            }.padding(vertical = 24.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Processo de Enfermagem",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                }

                items(viewState.subjectList) {
                    SubjectItem(
                        subject = SubjectUIModel(
                            id = it.id,
                            title = it.title,
                            backgroundColor = it.primaryColor,
                            features = it.features,
                            goToNursingDiagnostics = goToNursingDiagnosticList,
                            goToCaseStudies = goToCaseStudyList,
                        ),
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}