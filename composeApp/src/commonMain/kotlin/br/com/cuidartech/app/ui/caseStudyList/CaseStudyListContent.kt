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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text as Text3
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
import cuidartechapp.composeapp.generated.resources.icon_case_study
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseStudyListContent(
    title: String,
    navigateBack: () -> Unit,
    viewState: CaseStudyListViewModel.ViewState,
    onItemClick: (studyCaseId: String, title: String) -> Unit,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Scaffold(
        topBar = {
            CuidarTechAppBar(
                title = AppStrings.CaseStudyList.topBarTitle,
                navigateBackAction = navigateBack,
                contentColor = primaryColor,
            )
        },
    ) { paddingValues ->

        when (viewState) {
            is CaseStudyListViewModel.ViewState.Loading ->
                CircularProgressIndicator(
                    color = primaryColor,
                )

            is CaseStudyListViewModel.ViewState.Error -> Text(AppStrings.errorGeneric)
            is CaseStudyListViewModel.ViewState.Success ->
                LazyColumn(
                    contentPadding =
                        PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = paddingValues.calculateTopPadding(),
                            bottom = 16.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Header(
                            title = title,
                            titleColor = primaryColor,
                            description = AppStrings.CaseStudyList.headerDescription,
                        )
                        Spacer(Modifier.size(24.dp))
                    }

                    items(viewState.caseStudies, key = { it.id }) {
                        OutlinedCard(
                            onClick = {
                                onItemClick(
                                    it.remoteId,
                                    AppStrings.CaseStudyList.cardTitle(it.id),
                                )
                            },
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        modifier = Modifier.size(36.dp),
                                        painter = painterResource(Res.drawable.icon_case_study),
                                        tint = primaryColor,
                                        contentDescription = AppStrings.CaseStudyList.cardContentDescription,
                                    )
                                    Text3(
                                        text = AppStrings.CaseStudyList.cardTitle(it.id),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                                Text3(
                                    text = it.intro,
                                    maxLines = 2,
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
