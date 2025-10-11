package br.com.cuidartech.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cuidartech.app.data.SubjectRepository
import br.com.cuidartech.app.data.SubjectsPrefetchStore
import br.com.cuidartech.app.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val subjectRepository: SubjectRepository,
    private val subjectsPrefetchStore: SubjectsPrefetchStore,
) : ViewModel() {
    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state: StateFlow<ViewState> = _state.asStateFlow()

    init {
        loadSubjects()
    }

    fun retry() {
        loadSubjects()
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            _state.value = ViewState.Loading
            subjectRepository
                .getSubjects()
                .onSuccess { subjectList ->
                    subjectsPrefetchStore.store(subjectList)
                    _state.value = ViewState.Success(subjectList)
                }.onFailure {
                    _state.value = ViewState.Error
                }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState

        data object Error : ViewState

        data class Success(val subjectList: List<Subject>) : ViewState
    }
}
