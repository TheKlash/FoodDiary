package de.alekseipopov.fooddiary.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.base.UiState
import de.alekseipopov.fooddiary.ui.base.toUiState
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiEvents
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: DayRecordRepository,
    recordId: Int
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState<Day>>(UiState.Loading())
    val uiState: StateFlow<UiState<Day>>
        get() = _uiState


    private val _uiEvents = MutableStateFlow<DetailsUiEvents?>(null)
    val uiEvents: StateFlow<DetailsUiEvents?>
        get() = _uiEvents

    init {
        getDay(recordId)
    }

    private fun getDay(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getDay(id)
                .catch { exception ->
                    _uiState.value = UiState.Error(exception)
                    Log.e("Exception!", exception.localizedMessage ?: "")
                }
                .distinctUntilChanged()
                .collect {
                    _uiState.value = it.toUiState()
                }
        }

    }

    fun updateDate(time: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val day = (_uiState.value as UiState.Result<Day>).data
            day.time = time
            repository.updateDay(day)
            getDay(day.id)
        }
    }

    fun deleteDay() {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.Main) {
                val day = (_uiState.value as UiState.Result<Day>).data
                repository.deleteDay(day)
            }
            _uiEvents.value = DetailsUiEvents.NavigateBack()
        }
    }

    fun showEditEntryDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.value = DetailsUiEvents.ShowEditDateDialog()
        }
    }

    fun hideEditEntryDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.value = DetailsUiEvents.HideEditDateDialog()
        }
    }

    fun showDeleteDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.value = DetailsUiEvents.ShowDeleteDialog()
        }
    }

    fun hideDeleteDialog() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvents.value = DetailsUiEvents.HideDeleteDialog()
        }
    }

}