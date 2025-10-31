package br.com.cuidartech.app.ui.subjects

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.domain.model.SubjectFeatures
import cuidartechapp.composeapp.generated.resources.Res
import cuidartechapp.composeapp.generated.resources.background_pattern
import cuidartechapp.composeapp.generated.resources.icon_case_study
import cuidartechapp.composeapp.generated.resources.icon_nursing_diagnostic
import cuidartechapp.composeapp.generated.resources.icon_nursing_process
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.Text as Text3
import br.com.cuidartech.app.ui.strings.AppStrings

@Composable
fun HomeContent(
    viewState: HomeViewModel.ViewState,
    goToNursingProcessList: () -> Unit,
    goToCaseStudyList: (subjectId: String, title: String) -> Unit,
    goToNursingDiagnosticList: (subjectId: String, title: String) -> Unit,
    onExploreClick: () -> Unit,
) {
    when (viewState) {
        is HomeViewModel.ViewState.Loading -> LoadingState()
        is HomeViewModel.ViewState.Error -> ErrorState()
        is HomeViewModel.ViewState.Success ->
            HomeSuccessContent(
                subjects = viewState.subjectList,
                goToNursingProcessList = goToNursingProcessList,
                goToCaseStudyList = goToCaseStudyList,
                goToNursingDiagnosticList = goToNursingDiagnosticList,
                onExploreClick = onExploreClick,
            )
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(AppStrings.errorGeneric)
    }
}

@Composable
private fun HomeSuccessContent(
    subjects: List<Subject>,
    goToNursingProcessList: () -> Unit,
    goToCaseStudyList: (subjectId: String, title: String) -> Unit,
    goToNursingDiagnosticList: (subjectId: String, title: String) -> Unit,
    onExploreClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        HomeBackgroundPattern()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
            contentPadding = PaddingValues(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item { HomeWelcome() }
            item { HomeSearchExploreButton(onClick = onExploreClick) }
            item { HomeHeroCard(onStartClick = goToNursingProcessList) }
            item {
                Text3(
                    text = AppStrings.Home.homeSectionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            items(subjects, key = { it.id }) { subject ->
                SubjectCard(
                    subject = subject,
                    onCaseStudyClick = { goToCaseStudyList(subject.id, subject.title) },
                    onNursingDiagnosticClick = { goToNursingDiagnosticList(subject.id, subject.title) },
                )
            }
        }
    }
}

@Composable
private fun HomeWelcome() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text3(
            text = AppStrings.Home.welcomeTitle,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
        Text3(
            text = AppStrings.Home.welcomeSubtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeSearchSheetContent(
    query: String,
    filters: HomeSearchState.AppliedFilters,
    subjects: List<Subject>,
    searchState: HomeSearchState,
    onQueryChange: (String, Set<HomeSearchState.ResultType>, String?) -> Unit,
    onFilterChange: (String, Set<HomeSearchState.ResultType>, String?) -> Unit,
    onResultClick: (HomeSearchState.SearchItem) -> Unit,
) {
    val selectedTypes = filters.types
    val selectedSubjectId = filters.subjectId
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text3(
            text = AppStrings.Home.searchSheetTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { onQueryChange(it, selectedTypes, selectedSubjectId) },
            singleLine = true,
            placeholder = { Text3(AppStrings.Home.searchPlaceholder) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = AppStrings.Home.searchPlaceholder,
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("", selectedTypes, selectedSubjectId) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = AppStrings.Home.overlayDismiss,
                        )
                    }
                }
            }
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text3(
                text = AppStrings.Home.searchFilterType,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                HomeSearchState.ResultType.values().forEach { type ->
                    val isSelected = selectedTypes.contains(type)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            val updated = if (isSelected) selectedTypes - type else selectedTypes + type
                            if (updated.isNotEmpty()) {
                                onFilterChange(query, updated, selectedSubjectId)
                            }
                        },
                        label = { Text3(type.label(), style = MaterialTheme.typography.labelLarge) },
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text3(
                text = AppStrings.Home.searchFilterSubject,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                FilterChip(
                    selected = selectedSubjectId == null,
                    onClick = { onFilterChange(query, selectedTypes, null) },
                    label = { Text3(AppStrings.Home.searchFilterAllSubjects) },
                )
                subjects.forEach { subject ->
                    FilterChip(
                        selected = selectedSubjectId == subject.id,
                        onClick = { onFilterChange(query, selectedTypes, subject.id) },
                        label = { Text3(subject.title) },
                    )
                }
            }
        }

        SearchResultsContent(
            searchState = searchState,
            query = query,
            onResultClick = onResultClick,
        )
    }
}

@Composable
private fun SearchResultsContent(
    searchState: HomeSearchState,
    query: String,
    onResultClick: (HomeSearchState.SearchItem) -> Unit,
) {
    when (searchState) {
        HomeSearchState.Idle -> {
            Text3(
                text = AppStrings.Home.searchIdleHint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
            )
        }
        HomeSearchState.Loading -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        HomeSearchState.Error -> {
            Text3(
                text = AppStrings.Home.searchError,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
        is HomeSearchState.Results -> {
            if (searchState.items.isEmpty()) {
                Text3(
                    text = AppStrings.Home.searchNoResults(searchState.query),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HomeSearchState.ResultType.values().forEach { type ->
                        val itemsByType = searchState.items.filter { it.type == type }
                        if (itemsByType.isNotEmpty()) {
                            Text3(
                                text = type.label(),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                itemsByType.forEach { item ->
                                    SearchResultCard(
                                        item = item,
                                        query = query,
                                        onResultClick = onResultClick,
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

@Composable
private fun SearchResultCard(
    item: HomeSearchState.SearchItem,
    query: String,
    onResultClick: (HomeSearchState.SearchItem) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().clickable { onResultClick(item) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
        ),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val iconRes = when (item.type) {
                    HomeSearchState.ResultType.PROCESS -> Res.drawable.icon_nursing_process
                    HomeSearchState.ResultType.DIAGNOSTIC -> Res.drawable.icon_nursing_diagnostic
                    HomeSearchState.ResultType.CASE_STUDY -> Res.drawable.icon_case_study
                }
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp),
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text3(
                        text = highlightText(item.title, query),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                    item.subjectTitle?.let { subjectTitle ->
                        Text3(
                            text = subjectTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                        )
                    }
                }
            }
            item.subtitle?.let {
                Text3(
                    text = highlightText(it, query),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
            item.snippet?.let { snippet ->
                Text3(
                    text = highlightText(snippet, query),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
            Text3(
                text = item.type.label(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun HomeSearchExploreButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = AppStrings.Home.overlayTitle)
        Spacer(Modifier.width(8.dp))
        Text3(
            text = AppStrings.Home.exploreButton,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun BoxScope.HomeBackgroundPattern() {
    val tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    Image(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 12.dp)
            .size(160.dp),
        painter = painterResource(Res.drawable.background_pattern),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
    )
    Image(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(bottom = 12.dp)
            .size(160.dp),
        painter = painterResource(Res.drawable.background_pattern),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
    )
}

@Composable
private fun HomeHeroCard(onStartClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text3(
                text = AppStrings.Home.heroTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Text3(
                text = AppStrings.Home.heroSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
            )
            Button(onClick = onStartClick) {
                Text3(AppStrings.Home.heroButton)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SubjectCard(
    subject: Subject,
    onCaseStudyClick: () -> Unit,
    onNursingDiagnosticClick: () -> Unit,
) {
    val features = subject.features.orEmpty()
    val accentColor = subject.primaryColor?.toColorOrNull()
    val borderColor = accentColor?.copy(alpha = 0.35f) ?: MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(width = 1.dp, color = borderColor),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text3(
                    text = subject.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            subject.displayDescription()?.let { description ->
                Text3(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                )
            }

            if (features.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (SubjectFeatures.CASE_STUDIES in features) {
                        SubjectFeatureChip(
                            label = SubjectFeatures.CASE_STUDIES.displayLabel(),
                            onClick = onCaseStudyClick,
                        )
                    }
                    if (SubjectFeatures.NURSING_DIAGNOSTICS in features) {
                        SubjectFeatureChip(
                            label = SubjectFeatures.NURSING_DIAGNOSTICS.displayLabel(),
                            onClick = onNursingDiagnosticClick,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectFeatureChip(
    label: String,
    onClick: () -> Unit,
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text3(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        ),
    )
}

private fun Subject.displayDescription(): String? {
    val featureSet = features.orEmpty()
    return when {
        featureSet.containsAll(
            setOf(SubjectFeatures.CASE_STUDIES, SubjectFeatures.NURSING_DIAGNOSTICS),
        ) -> AppStrings.SubjectDescription.caseAndDiagnostic
        featureSet.contains(SubjectFeatures.CASE_STUDIES) -> AppStrings.SubjectDescription.caseOnly
        featureSet.contains(SubjectFeatures.NURSING_DIAGNOSTICS) -> AppStrings.SubjectDescription.diagnosticOnly
        else -> null
    }
}

private fun Long.toColorOrNull(): Color? {
    if (this == 0L) return null
    val argb = if ((this ushr 24) == 0L) this or 0xFF000000 else this
    val alpha = ((argb shr 24) and 0xFF).toFloat() / 255f
    val red = ((argb shr 16) and 0xFF).toFloat() / 255f
    val green = ((argb shr 8) and 0xFF).toFloat() / 255f
    val blue = (argb and 0xFF).toFloat() / 255f
    return Color(red = red, green = green, blue = blue, alpha = alpha)
}

@Composable
private fun highlightText(text: String, query: String): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(text)

    val lowerText = text.lowercase()
    val lowerQuery = query.lowercase()
    val highlightColor = MaterialTheme.colorScheme.primary

    val builder = buildAnnotatedString {
        var currentIndex = 0
        while (currentIndex < text.length) {
            val matchIndex = lowerText.indexOf(lowerQuery, currentIndex)
            if (matchIndex < 0) {
                append(text.substring(currentIndex))
                break
            }
            append(text.substring(currentIndex, matchIndex))
            withStyle(SpanStyle(color = highlightColor, fontWeight = FontWeight.SemiBold)) {
                append(text.substring(matchIndex, matchIndex + lowerQuery.length))
            }
            currentIndex = matchIndex + lowerQuery.length
        }
    }
    return builder
}

private fun HomeSearchState.ResultType.label(): String =
    when (this) {
        HomeSearchState.ResultType.PROCESS -> AppStrings.Home.searchChipProcess
        HomeSearchState.ResultType.DIAGNOSTIC -> AppStrings.Home.searchChipDiagnostic
        HomeSearchState.ResultType.CASE_STUDY -> AppStrings.Home.searchChipCaseStudy
    }

private fun SubjectFeatures.displayLabel(): String =
    when (this) {
        SubjectFeatures.CASE_STUDIES -> AppStrings.Home.featureCaseStudies
        SubjectFeatures.NURSING_DIAGNOSTICS -> AppStrings.Home.featureNursingDiagnostics
    }
