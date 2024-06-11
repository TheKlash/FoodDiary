package de.alekseipopov.fooddiary.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiEvents
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: DayRecordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState>
        get() = _uiState

    private val _uiEvents = Channel<DetailsUiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun getDay(id: Int) {
        _uiState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDay(id)
                .catch { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = exception.localizedMessage
                        )
                    }
                    Log.e("Exception!", exception.localizedMessage ?: "")
                }
                .collect {
                    _uiState.update { state -> state.copy(isLoading = false, record = it) }
                }
        }

    }

    fun showEditEntryDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.send(DetailsUiEvents.ShowEditDateDialog())
        }
    }

    fun hideEditEntryDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.send(DetailsUiEvents.HideEditDateDialog())
        }
    }

}