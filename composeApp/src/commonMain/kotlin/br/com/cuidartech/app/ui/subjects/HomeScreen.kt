package br.com.cuidartech.app.ui.subjects

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text as Text3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.cuidartech.app.domain.model.Subject
import br.com.cuidartech.app.ui.strings.AppStrings
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    goToDiagnosticList: (subjectId: String, title: String) -> Unit,
    goToDiagnostic: (diagnosticPath: String) -> Unit,
    goToCaseStudyList: (subjectId: String, title: String) -> Unit,
    goToCaseStudy: (caseStudyPath: String, title: String) -> Unit,
    goToNursingProcessList: () -> Unit,
    goToNursingProcess: (processId: String) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val searchViewModel: HomeSearchViewModel = koinViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val searchState by searchViewModel.searchState.collectAsStateWithLifecycle()
    val searchQuery by searchViewModel.query.collectAsStateWithLifecycle()
    val filters by searchViewModel.filters.collectAsStateWithLifecycle()
    val subjects by searchViewModel.subjects.collectAsStateWithLifecycle()

    val displayedSubjects =
        if (subjects.isEmpty() && state is HomeViewModel.ViewState.Success) {
            (state as HomeViewModel.ViewState.Success).subjectList
        } else {
            subjects
        }

    var isSearchVisible by rememberSaveable { mutableStateOf(false) }

    HomeContent(
        viewState = state,
        goToCaseStudyList = goToCaseStudyList,
        goToNursingProcessList = goToNursingProcessList,
        goToNursingDiagnosticList = goToDiagnosticList,
        onExploreClick = { isSearchVisible = true },
    )

    if (state is HomeViewModel.ViewState.Success) {
        val subjectList = (state as HomeViewModel.ViewState.Success).subjectList
        LaunchedEffect(subjectList) {
            searchViewModel.setSubjects(subjectList)
        }
    }

    AnimatedVisibility(
        visible = isSearchVisible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
    ) {
        HomeSearchOverlay(
            query = searchQuery,
            filters = filters,
            subjects = displayedSubjects,
            searchState = searchState,
            onDismissRequest = { isSearchVisible = false },
            onQueryChange = { query, types, subjectId ->
                searchViewModel.onQueryChange(query, types, subjectId)
            },
            onFilterChange = { query, types, subjectId ->
                searchViewModel.onFiltersChange(query, types, subjectId)
            },
            onResultClick = { item ->
                when (item.type) {
                    HomeSearchState.ResultType.PROCESS ->
                        goToNursingProcess(item.id)
                    HomeSearchState.ResultType.DIAGNOSTIC ->
                        goToDiagnostic(item.id)
                    HomeSearchState.ResultType.CASE_STUDY ->
                        goToCaseStudy(
                            item.id,
                            item.title.orEmpty(),
                        )
                }
                isSearchVisible = false
            },
        )
    }
}

@Composable
private fun HomeSearchOverlay(
    query: String,
    filters: HomeSearchState.AppliedFilters,
    subjects: List<Subject>,
    searchState: HomeSearchState,
    onDismissRequest: () -> Unit,
    onQueryChange: (String, Set<HomeSearchState.ResultType>, String?) -> Unit,
    onFilterChange: (String, Set<HomeSearchState.ResultType>, String?) -> Unit,
    onResultClick: (HomeSearchState.SearchItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = AppStrings.Home.overlayDismiss,
                        )
                    }
                    Text3(
                        modifier = Modifier.padding(start = 8.dp),
                        text = AppStrings.Home.overlayTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                HomeSearchSheetContent(
                    query = query,
                    filters = filters,
                    subjects = subjects,
                    searchState = searchState,
                    onQueryChange = onQueryChange,
                    onFilterChange = onFilterChange,
                    onResultClick = onResultClick,
                )
            }
        }
    }
}
