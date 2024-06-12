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
import kotlinx.coroutines.flow.distinctUntilChanged
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
        viewModelScope.launch(Dispatchers.Main) {
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
                .distinctUntilChanged()
                .collect {
                    _uiState.update { state -> state.copy(isLoading = false, record = it) }
                }
        }

    }

    fun updateDate(time: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val day = _uiState.value.record
            day?.let {
                it.time = time
                repository.updateDay(it)
                getDay(it.id)
            }
        }
    }

    fun deleteDay() {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.Main) {
                val day = _uiState.value.record
                day?.let {
                    repository.deleteDay(it)
                }
            }
            _uiEvents.send(DetailsUiEvents.NavigateBack())
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

    fun showDeleteDialog(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.send(DetailsUiEvents.ShowDeleteDialog())
        }
    }

    fun hideDeleteDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.send(DetailsUiEvents.HideDeleteDialog())
        }
    }

}