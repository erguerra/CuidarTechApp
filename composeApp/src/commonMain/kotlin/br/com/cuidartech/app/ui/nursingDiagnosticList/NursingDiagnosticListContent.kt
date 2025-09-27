package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.Header
import br.com.cuidartech.app.ui.model.NursingDiagnosticCategoryDivider
import br.com.cuidartech.app.ui.model.NursingDiagnosticItemUiModel
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
        },
    ) { paddingValues ->

        when (viewState) {
            is NursingDiagnosticListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is NursingDiagnosticListViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is NursingDiagnosticListViewModel.ViewState.Success ->
                LazyColumn(
                    contentPadding =
                        PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Header(
                            title = title,
                            titleColor = primaryColor,
                        )
                    }

                    items(viewState.diagnosticList, key = { it.title }) { diagnosticItem ->
                        when (diagnosticItem) {
                            is NursingDiagnosticCategoryDivider ->
                                Text(
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                                    text = diagnosticItem.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = primaryColor?.copy(alpha = 0.8f)
                                        ?: MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                )


                            is NursingDiagnosticItemUiModel ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.background,
                                    shape = MaterialTheme.shapes.medium,
                                    elevation = 2.dp,
                                    border = BorderStroke(
                                        1.dp,
                                        primaryColor ?: MaterialTheme.colorScheme.primary,
                                    ),
                                    onClick = {
                                        onItemClick(
                                            diagnosticItem.remoteId,
                                            primaryColor?.toArgb()?.toLong(),
                                        )
                                    },
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).padding(8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(Res.drawable.icon_nursing_diagnostic),
                                            tint = primaryColor
                                                ?: MaterialTheme.colorScheme.primary,
                                            contentDescription = "Diagnóstico",
                                        )
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = diagnosticItem.title,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            color = primaryColor
                                                ?: MaterialTheme.colorScheme.primary,
                                        )
                                    }
                                }
                        }
                    }
                }
        }
    }
}
