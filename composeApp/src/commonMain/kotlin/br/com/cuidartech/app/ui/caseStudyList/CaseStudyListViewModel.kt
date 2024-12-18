package br.com.cuidartech.app.ui.caseStudyList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.CaseStudy
import br.com.cuidartech.app.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CaseStudyListViewModel(
    private val repository: SubjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private val subjectId = savedStateHandle.toRoute<Route.CaseStudyListRoute>().subjectId

    init {
        getCaseStudyList()
    }

    private fun getCaseStudyList() {
        viewModelScope.launch {
            repository.getCaseStudiesBySubject(subjectId).onSuccess { studyList ->
                _uiState.update {
                    ViewState.Success(
                        caseStudies = studyList,
                    )
                }
            }.onFailure {
                _uiState.update { ViewState.Error }
            }
        }
    }

    sealed interface ViewState {
        data class Success(val caseStudies: List<CaseStudy>) : ViewState
        data object Loading: ViewState
        data object Error: ViewState
    }
}