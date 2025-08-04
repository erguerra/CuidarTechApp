package br.com.cuidartech.app.ui.nursingDiagnostic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NursingDiagnosticViewModel(
    private val repository: SubjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val nursingDiagnosticPath = savedStateHandle.toRoute<Route.DiagnosticRoute>().diagnosticPath

    init {
        loadDiagnostic()
    }

    private fun loadDiagnostic() {
        viewModelScope.launch {
            repository
                .getNursingDiagnosticByPath(nursingDiagnosticPath)
                .onSuccess { nursingDiagnostic ->
                    _uiState.update {
                        ViewState.Success(nursingDiagnostic)
                    }
                }.onFailure {
                    _uiState.update {
                        ViewState.Error
                    }
                }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState

        data object Error : ViewState

        data class Success(
            val nursingDiagnostic: NursingDiagnostic,
        ) : ViewState
    }
}
