package br.com.cuidartech.app.ui.caseStudy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.Alternative
import br.com.cuidartech.app.domain.model.CaseStudy
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CaseStudyViewModel(
    private val repository: SubjectRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
    )
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val caseStudyPath = savedStateHandle.toRoute<Route.CaseStudyRoute>().caseStudyPath

    init {
        loadCaseStudy()
    }

    private fun loadCaseStudy() {
        viewModelScope.launch {
            repository.getCaseStudyByPath(caseStudyPath).onSuccess { caseStudy ->
                _uiState.update {
                    ViewState.Success(caseStudy = caseStudy)
                }
            }.onFailure {
                _uiState.update {
                    ViewState.Error
                }
            }
        }
    }

    fun evaluateAnswer(alternative: Alternative) {
        with(alternative) {
            if (isCorrect) {
                showRightAnswerFeedback()
            } else {
                showWrongAnswerFeedback()
            }
        }
    }

    private fun showRightAnswerFeedback() {
        _event.tryEmit(Event.ShowRightAnswerFeedback)
    }

    private fun showWrongAnswerFeedback() {
        _event.tryEmit(Event.ShowWrongAnswerFeedback)
    }

    sealed interface Event {
        data object ShowRightAnswerFeedback : Event
        data object ShowWrongAnswerFeedback : Event
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data object Error : ViewState
        data class Success(val caseStudy: CaseStudy) : ViewState
    }

}