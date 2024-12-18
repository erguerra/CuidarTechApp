package br.com.cuidartech.app.ui.nursingDiagnostics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.NursingDiagnostic
import br.com.cuidartech.app.ui.caseStudyList.CaseStudyListViewModel.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NursingDiagnosticViewModel(
    private val repository: SubjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()




    sealed interface ViewState {
        data class Success (val subjectList: List<NursingDiagnostic>): ViewState
        data object Loading: ViewState
        data object Error: ViewState
    }
}