package br.com.cuidartech.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cuidartech.app.data.NursingProcessRepository
import br.com.cuidartech.app.domain.model.NursingProcess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NursingProcessListViewModel(private val repository: NursingProcessRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    fun getNursingList() {
        viewModelScope.launch {
            repository.getNursingProcessList().onSuccess { list ->
                _uiState.update {
                    ViewState.Success(list)
                }
            }.onFailure {
                _uiState.update {
                    ViewState.Error
                }
            }
        }
    }

    sealed interface ViewState {

        data class Success(val nursingProcessList: List<NursingProcess>) : ViewState
        data object Loading : ViewState
        data object Error: ViewState
    }
}