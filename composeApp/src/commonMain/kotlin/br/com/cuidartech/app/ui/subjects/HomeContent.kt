package br.com.cuidartech.app.ui.subjects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.ui.model.SubjectUIModel

@Composable
fun HomeContent(
    viewState: HomeViewModel.ViewState,
    goToNursingProcessList: () -> Unit,
    goToCaseStudyList: (title: String, subjectId: String) -> Unit,
    goToNursingDiagnosticList: (subjectId: String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (viewState) {
            is HomeViewModel.ViewState.Loading -> CircularProgressIndicator()
            is HomeViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is HomeViewModel.ViewState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

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