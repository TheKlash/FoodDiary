package de.alekseipopov.fooddiary.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.domain.DayRecordRepository
import de.alekseipopov.fooddiary.ui.overview.model.OverviewUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val repository: DayRecordRepository
) : ViewModel() {

    val uiState: StateFlow<OverviewUiState>
        get() = _uiState.asStateFlow()
    private var _uiState: MutableStateFlow<OverviewUiState> = MutableStateFlow(OverviewUiState())

    fun getRecords() {
        _uiState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordsList()
                .catch { exception ->
                    _uiState.update { state ->
                        state.copy(isLoading = false, errorMessage = exception.localizedMessage)
                    }
                    Log.e("Exception!", exception.localizedMessage ?: "")
                }
                .collect { list ->
                    _uiState.update { state -> state.copy(isLoading = false, recordList = list) }
                }
        }
    }

    fun showDatePickerDialog() {
        _uiState.update {  state -> state.copy(showDatePickerDialog = true) }
    }

    fun hideDatePickerDialog() {
        _uiState.update {  state -> state.copy(showDatePickerDialog = false) }
    }

}