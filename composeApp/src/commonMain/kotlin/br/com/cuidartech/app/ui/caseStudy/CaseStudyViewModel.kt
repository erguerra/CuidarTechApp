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


    private val _uiState = MutableStateFlow(ViewState())
    val uiState = _uiState.asStateFlow()

    private var isLoading
        set(value) = _uiState.update {
            it.copy(isLoading = value)
        }
        get() = _uiState.value.isLoading

    private val caseStudyPath = savedStateHandle.toRoute<Route.CaseStudyRoute>().caseStudyPath

    init {
        loadCaseStudy()
    }

    private fun loadCaseStudy() {
        viewModelScope.launch {
            isLoading = true
            repository.getCaseStudyByPath(caseStudyPath).onSuccess { caseStudy ->
                handleSuccess(caseStudy)
            }.onFailure {
                handleFailure(it)
            }
        }
    }

    private fun handleSuccess(caseStudy: CaseStudy) {
        _uiState.update {
            it.copy(
                isLoading = false,
                caseStudy = caseStudy,
                error = null,
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                caseStudy = null,
                error = error,
            )
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

    fun closeDialog() {
        _uiState.update {
            it.copy(
                showDialog = false,
                dialogMessage = null,
            )
        }
    }

    private fun showRightAnswerFeedback() {
        _uiState.update {
            it.copy(
                showDialog = true,
                dialogMessage = it.caseStudy?.explanation,
                dialogVariant = FeedbackDialogVariant.RIGHT_ANSWER,
            )
        }
    }

    private fun showWrongAnswerFeedback() {
        _uiState.update {
            it.copy(
                showDialog = true,
                dialogMessage = it.caseStudy?.helper,
                dialogVariant = FeedbackDialogVariant.WRONG_ANSWER,
            )
        }
    }

    data class ViewState (
        val caseStudy: CaseStudy? = null,
        val isLoading: Boolean = false,
        val showDialog: Boolean = false,
        val dialogMessage: String? = null,
        val dialogVariant: FeedbackDialogVariant = FeedbackDialogVariant.WRONG_ANSWER,
        val error: Throwable? = null,
    )


}