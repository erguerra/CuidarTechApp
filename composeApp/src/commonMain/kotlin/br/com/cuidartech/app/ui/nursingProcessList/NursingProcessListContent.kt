package br.com.cuidartech.app.ui.nursingProcessList

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text as Text3
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.Header
import br.com.cuidartech.app.ui.strings.AppStrings
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_nursing_process
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingProcessListContent(
    state: NursingProcessListViewModel.ViewState,
    navigateBack: () -> Unit,
    onItemClick: (nursingProcessId: String) -> Unit = {},
) {
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = AppStrings.NursingProcessList.topBarTitle,
                navigateBackAction = navigateBack,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is NursingProcessListViewModel.ViewState.Loading -> CircularProgressIndicator()
                is NursingProcessListViewModel.ViewState.Error -> Text(AppStrings.errorGeneric)
                is NursingProcessListViewModel.ViewState.Success ->
                    LazyColumn(
                        contentPadding =
                            PaddingValues(
                                top = paddingValues.calculateTopPadding(),
                            ),
                        modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        item {
                            Header(
                                title = AppStrings.NursingProcessList.headerTitle,
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }

                        items(state.nursingProcessList) {
                            OutlinedCard(
                                onClick = { onItemClick(it.remoteId) },
                                shape = RoundedCornerShape(18.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                                ),
                                elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ),
                            ) {
                                Column(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 18.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                    Icon(
                                        modifier = Modifier.size(38.dp),
                                        painter = painterResource(Res.drawable.icon_nursing_process),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = AppStrings.NursingProcessList.itemContentDescription,
                                    )
                                        Text3(
                                            text = it.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                    Text3(
                                        text = it.body,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}
