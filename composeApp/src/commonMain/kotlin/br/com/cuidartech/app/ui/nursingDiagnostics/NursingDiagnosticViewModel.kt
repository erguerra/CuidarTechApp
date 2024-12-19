package br.com.cuidartech.app.ui.nursingDiagnostics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListViewModel.ViewState
import br.com.cuidartech.app.ui.model.NursingDiagnosticItemUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NursingDiagnosticViewModel(
    private val repository: SubjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    val subjectId = savedStateHandle.toRoute<Route.DiagnosticListRoute>().subjectId

    fun getDiagnosticList() {
        viewModelScope.launch {
            repository.getNursingDiagnosticsBySubject(subjectId).onSuccess { diagnosticList ->
                _uiState.update {
                    ViewState.Success(
                        diagnosticList = diagnosticList.map {
                            NursingDiagnosticItemUiModel(
                                id = it.id,
                                remoteId = it.remoteId,
                                title = it.title,
                            )
                        }
                    )
                }
            }.onFailure {
                _uiState.update {  ViewState.Error }
            }
        }
    }


    sealed interface ViewState {
        data class Success (val diagnosticList: List<NursingDiagnosticItemUiModel>): ViewState
        data object Loading: ViewState
        data object Error: ViewState
    }
}