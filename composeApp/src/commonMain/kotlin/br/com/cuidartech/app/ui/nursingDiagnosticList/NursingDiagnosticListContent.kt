package br.com.cuidartech.app.ui.nursingDiagnosticList

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
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.Header
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_nursing_diagnostic
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NursingDiagnosticListContent(
    primaryColor: Color?,
    title: String,
    viewState: NursingDiagnosticListViewModel.ViewState,
    onItemClick: (diagnosticPath: String, primaryColor: Long?) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = "Diagnósticos",
                navigateBackAction = navigateBack,
                contentColor = primaryColor,
            )
        }
    ) { paddingValues ->

        when (viewState) {
            is NursingDiagnosticListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is NursingDiagnosticListViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is NursingDiagnosticListViewModel.ViewState.Success -> LazyColumn(
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
                    )
                    Spacer(Modifier.size(24.dp))
                }

                items(viewState.diagnosticList, key = { it.id }) {
                    Surface(
                        color = primaryColor ?: MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            onItemClick(it.remoteId, primaryColor?.toArgb()?.toLong())
                        }
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
                                    painter = painterResource(Res.drawable.icon_nursing_diagnostic),
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = "Diagnóstico",
                                )
                                Text(
                                    text = it.title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}