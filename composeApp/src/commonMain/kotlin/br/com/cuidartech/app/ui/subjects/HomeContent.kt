package br.com.cuidartech.app.ui.subjects

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.ui.model.SubjectUIModel
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.background_pattern
import cuidartechapp.composeapp.generated.resources.icon_nursing_process
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeContent(
    viewState: HomeViewModel.ViewState,
    goToNursingProcessList: () -> Unit,
    goToCaseStudyList: (subjectId: String, title: String, primaryColorLong: Long?) -> Unit,
    goToNursingDiagnosticList: (subjectId: String, title: String, primaryColor: Long?) -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (viewState) {
            is HomeViewModel.ViewState.Loading -> CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
            )

            is HomeViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is HomeViewModel.ViewState.Success -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(Res.drawable.background_pattern),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).align(Alignment.TopEnd),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                )
                Image(
                    painter = painterResource(Res.drawable.background_pattern),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).align(Alignment.BottomStart),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 4.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().clickable {
                                    goToNursingProcessList()
                                }.padding(vertical = 24.dp, horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.icon_nursing_process),
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = null,
                                )
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
                                backgroundColor = it.primaryColor ?: MaterialTheme.colorScheme.primary.toArgb().toLong(),
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

}