package br.com.cuidartech.app.ui.nursingDiagnosticList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
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

    fun getDiagnosticList() {
        viewModelScope.launch {
            repository
                .getNursingDiagnosticsBySubject(subjectId)
                .onSuccess { diagnosticList ->
                    val uiList =
                        nursingDiagnosticUIModelMapper.transformToCategorizedList(diagnosticList)
                    _uiState.update {
                        ViewState.Success(uiList)
                    }
                }.onFailure {
                    _uiState.update { ViewState.Error }
                }
        }
    }

    sealed interface ViewState {
        data class Success(
            val diagnosticList: List<NursingDiagnosticListItem>,
        ) : ViewState

        data object Loading : ViewState

        data object Error : ViewState
    }
}
