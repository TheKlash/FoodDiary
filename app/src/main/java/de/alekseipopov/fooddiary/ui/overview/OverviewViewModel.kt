package de.alekseipopov.fooddiary.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.domain.DayRecordRepository
import de.alekseipopov.fooddiary.ui.base.UiState
import de.alekseipopov.fooddiary.ui.base.toUiState
import de.alekseipopov.fooddiary.ui.overview.model.OverviewUiEvents
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

    val uiState: StateFlow<UiState<List<DayRecord>>>
        get() = _uiState.asStateFlow()
    private var _uiState  = MutableStateFlow<UiState<List<DayRecord>>>(UiState.Loading())
    val uiEvents: StateFlow<OverviewUiEvents?>
        get() = _uiEvents.asStateFlow()
    private var _uiEvents = MutableStateFlow<OverviewUiEvents?>(null)

    init {
        getRecords()
    }

    fun getRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading()
            repository.getRecordsList()
                .catch { _uiState.value = it.toUiState() }
                .collect { _uiState.value = it.orEmpty().toUiState() }
        }
    }

    fun showReportDatePickerDialog() {
        _uiEvents.update { state -> OverviewUiEvents.ShowReportDatePickerDialog() }
    }

    fun hideReportDatePickerDialog() {
        _uiEvents.update {  state -> OverviewUiEvents.HideReportDatePickerDialog() }
    }

    fun showNewEntryDialog() {
        _uiEvents.update { state -> OverviewUiEvents.ShowNewEntryDialog() }
    }

    fun hideNewEntryDialog() {
        _uiEvents.update { state -> OverviewUiEvents.HideNewEntryDialog() }
    }
}