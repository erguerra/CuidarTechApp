package br.com.cuidartech.app.ui.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val subjectRepository: SubjectRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        loadSubjects()
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            subjectRepository
                .getSubjects()
                .onSuccess { subjectList ->
                    _viewState.update { ViewState.Success(subjectList) }
                }.onFailure {
                    _viewState.update { ViewState.Error }
                }
        }
    }

    sealed interface ViewState {
        data class Success(
            val subjectList: List<Subject>,
        ) : ViewState

        data object Loading : ViewState

        data object Error : ViewState
    }
}
