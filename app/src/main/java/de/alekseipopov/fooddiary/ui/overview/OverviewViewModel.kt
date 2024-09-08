package de.alekseipopov.fooddiary.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.core.ui.data.UiState
import de.alekseipopov.fooddiary.core.ui.data.toUiState
import de.alekseipopov.fooddiary.ui.overview.model.OverviewUiEvents
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.navigation.DetailsScreenRoute
import de.alekseipopov.fooddiary.ui.navigation.MainCoordinator
import de.alekseipopov.fooddiary.ui.navigation.NavEvent
import de.alekseipopov.fooddiary.ui.navigation.ReportScreenRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val coordinator: MainCoordinator,
    private val repository: DayRecordRepository
) : ViewModel() {

    val uiState: StateFlow<UiState<List<Day>>>
        get() = _uiState.asStateFlow()
    private var _uiState = MutableStateFlow<UiState<List<Day>>>(UiState.Loading())
    val uiEvents: StateFlow<OverviewUiEvents?>
        get() = _uiEvents.asStateFlow()
    private var _uiEvents = MutableStateFlow<OverviewUiEvents?>(null)

    init {
        getRecords()
    }

    fun getRecords() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading()
            repository.getDayRecords()
                .catch { _uiState.value = UiState.Error(it) }
                .distinctUntilChanged()
                .collect { _uiState.value = it.toUiState() }
        }
    }

    fun createNewDay(date: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val newDayId = repository.createNewDay(date)
            _uiEvents.value = OverviewUiEvents.ShowNewDay(newDayId)
            coordinator.navigate(
                NavEvent.ComposeScreen(
                    route = DetailsScreenRoute,
                    args = DetailsScreenRoute.Args(newDayId)
                )
            )
        }
    }

    fun onDayRecordSelected(id: Int) {
        viewModelScope.launch {
            coordinator.navigate(
                NavEvent.ComposeScreen(
                    route = DetailsScreenRoute,
                    args = DetailsScreenRoute.Args(id)
                )
            )
        }
    }

    fun onReportSelected(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            coordinator.navigate(
                NavEvent.ComposeScreen(
                    route = ReportScreenRoute,
                    args = ReportScreenRoute.Args(
                        startDate = startDate,
                        endDate = endDate
                    )
                )
            )
        }
    }

    fun showReportDatePickerDialog() {
        _uiEvents.update { OverviewUiEvents.ShowReportDatePickerDialog() }
    }

    fun hideReportDatePickerDialog() {
        _uiEvents.update { OverviewUiEvents.HideReportDatePickerDialog() }
    }

    fun showNewEntryDialog() {
        _uiEvents.update { OverviewUiEvents.ShowNewEntryDialog() }
    }

    fun hideNewEntryDialog() {
        _uiEvents.update { OverviewUiEvents.HideNewEntryDialog() }
    }
}