package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.mappers.NursingDiagnosticUIModelMapper
import br.com.cuidartech.app.ui.model.NursingDiagnosticListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NursingDiagnosticListViewModel(
    private val repository: SubjectRepository,
    private val nursingDiagnosticUIModelMapper: NursingDiagnosticUIModelMapper,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private val subjectId = savedStateHandle.toRoute<Route.DiagnosticListRoute>().subjectId

    private var fullDiagnosticList: List<NursingDiagnostic> = emptyList()
    private var currentQuery: String = ""

    fun getDiagnosticList() {
        viewModelScope.launch {
            repository
                .getNursingDiagnosticsBySubject(subjectId)
                .onSuccess { diagnosticList ->
                    fullDiagnosticList = diagnosticList
                    emitSuccess()
                }.onFailure {
                    _uiState.update { ViewState.Error }
                }
        }
    }

    fun onQueryChange(query: String) {
        if (fullDiagnosticList.isEmpty() && query.isNotBlank()) {
            currentQuery = query
            return
        }
        if (query == currentQuery && _uiState.value is ViewState.Success) return
        currentQuery = query
        emitSuccess()
    }

    private fun emitSuccess() {
        val filteredList = filterDiagnostics(currentQuery)
        _uiState.update {
            ViewState.Success(
                diagnosticList = filteredList,
                query = currentQuery,
            )
        }
    }

    private fun filterDiagnostics(query: String): List<NursingDiagnosticListItem> {
        val baseList =
            if (query.isBlank()) {
                fullDiagnosticList
            } else {
                val normalizedQuery = query.trim().lowercase()
                fullDiagnosticList.filter { diagnostic ->
                    diagnostic.title.contains(normalizedQuery, ignoreCase = true) ||
                        diagnostic.category.contains(normalizedQuery, ignoreCase = true)
                }
            }

        return nursingDiagnosticUIModelMapper.transformToCategorizedList(baseList)
    }

    sealed interface ViewState {
        data class Success(
            val diagnosticList: List<NursingDiagnosticListItem>,
            val query: String,
        ) : ViewState

        data object Loading : ViewState

        data object Error : ViewState
    }
}
