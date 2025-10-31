package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text as Text3
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import br.com.cuidartech.app.ui.components.CuidarTechAppBar
import br.com.cuidartech.app.ui.components.Header
import br.com.cuidartech.app.ui.model.NursingDiagnosticCategoryDivider
import br.com.cuidartech.app.ui.model.NursingDiagnosticItemUiModel
import br.com.cuidartech.app.ui.strings.AppStrings
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.icon_nursing_diagnostic
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NursingDiagnosticListContent(
    title: String,
    viewState: NursingDiagnosticListViewModel.ViewState,
    onItemClick: (diagnosticPath: String) -> Unit,
    navigateBack: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = AppStrings.NursingDiagnosticList.topBarTitle,
                navigateBackAction = navigateBack,
                contentColor = primaryColor,
            )
        },
    ) { paddingValues ->
        when (viewState) {
            is NursingDiagnosticListViewModel.ViewState.Loading ->
                CircularProgressIndicator(color = primaryColor)

            is NursingDiagnosticListViewModel.ViewState.Error ->
                Text(AppStrings.errorGeneric)

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
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = searchQuery,
                            onValueChange = onSearchQueryChange,
                            singleLine = true,
                            label = { Text3(AppStrings.NursingDiagnosticList.searchLabel) },
                            placeholder = { Text3(AppStrings.NursingDiagnosticList.searchPlaceholder) },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = primaryColor,
                                cursorColor = primaryColor,
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (viewState.diagnosticList.isEmpty()) {
                        item {
                            Text3(
                                modifier = Modifier.padding(top = 24.dp),
                                text = AppStrings.NursingDiagnosticList.emptyMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            )
                        }
                    } else {
                        items(viewState.diagnosticList, key = { it.title }) { diagnosticItem ->
                            when (diagnosticItem) {
                                is NursingDiagnosticCategoryDivider ->
                                    Text(
                                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                                        text = diagnosticItem.title,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    )

                                is NursingDiagnosticItemUiModel ->
                                    OutlinedCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = { onItemClick(diagnosticItem.remoteId) },
                                        shape = RoundedCornerShape(18.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                        ),
                                        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp),
                                        colors = CardDefaults.outlinedCardColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                        ),
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(min = 56.dp)
                                                .padding(horizontal = 16.dp, vertical = 12.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(Res.drawable.icon_nursing_diagnostic),
                                            tint = primaryColor,
                                            contentDescription = AppStrings.NursingDiagnosticList.itemContentDescription,
                                        )
                                            Text3(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = diagnosticItem.title,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }
                                    }
                            }
                        }
                    }
                }
        }
    }
}
