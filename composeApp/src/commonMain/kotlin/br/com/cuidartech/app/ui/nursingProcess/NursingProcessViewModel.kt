package br.com.cuidartech.app.ui.nursingProcess

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.cuidartech.app.app.navigation.Route
import br.com.cuidartech.app.data.NursingProcessRepository
import br.com.cuidartech.app.domain.model.NursingProcess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NursingProcessViewModel(
    private val nursingProcessRepository: NursingProcessRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Loading)
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> =
        MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
        )
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val nursingProcessId = savedStateHandle.toRoute<Route.NursingProcessRoute>().nursingProcessId

    init {
        loadNursingProcess()
    }

    fun launchUrl(url: String) {
        _event.tryEmit(Event.LaunchReferenceUrl(url))
    }

    private fun loadNursingProcess() {
        viewModelScope.launch {
            nursingProcessRepository
                .getNursingProcessById(nursingProcessId)
                .onSuccess { nursingProcess ->
                    val nursingProcessIdented = nursingProcess.copy(body = nursingProcess.body.replace("\\n", "\n"))
                    _uiState.update {
                        ViewState.Success(nursingProcess = nursingProcessIdented)
                    }
                }.onFailure {
                    _uiState.update {
                        ViewState.Error
                    }
                }
        }
    }

    sealed interface ViewState {
        data object Error : ViewState

        data object Loading : ViewState

        data class Success(
            val nursingProcess: NursingProcess,
        ) : ViewState
    }

    sealed interface Event {
        data class LaunchReferenceUrl(
            val url: String,
        ) : Event
    }
}
