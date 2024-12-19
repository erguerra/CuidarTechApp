package br.com.cuidartech.app.ui.caseStudyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.Header
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_case_study
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CaseStudyListContent(
    title: String,
    primaryColor: Color?,
    navigateBack: () -> Unit,
    viewState: CaseStudyListViewModel.ViewState,
    onItemClick: (String) -> Unit,
) {

    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = "Estudos de Caso",
                navigateBackAction = navigateBack,
                contentColor = primaryColor,
            )
        }
    ) { paddingValues ->

        when (viewState) {
            is CaseStudyListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is CaseStudyListViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is CaseStudyListViewModel.ViewState.Success -> LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Header(
                        title = title,
                        titleColor = primaryColor,
                        description = "Leia atentamente as informações do Estudo de Caso e selecione uma das opções de diagnóstico apresentadas. Caso a opção esteja incorreta, você visualizará uma dica para a seleção do diagnóstico adequado."
                    )
                    Spacer(Modifier.size(24.dp))
                }

                items(viewState.caseStudies, key = { it.id }) {
                    Surface(
                        color = primaryColor ?: MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium,
                        onClick = { onItemClick(it.remoteId) }
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
                                    painter = painterResource(Res.drawable.icon_case_study),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = "Estudo de Caso",
                                )
                                Text(
                                    text = "Estudo de Caso ${it.id}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = it.intro,
                                maxLines = 2,
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
