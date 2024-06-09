package de.alekseipopov.fooddiary.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    val repository: DayRecordRepository
) : ViewModel() {

    val uiState: StateFlow<DetailsUiState>
        get() = _uiState
    private val _uiState = MutableStateFlow(DetailsUiState())

    fun getRecord(id: Int) {
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
}