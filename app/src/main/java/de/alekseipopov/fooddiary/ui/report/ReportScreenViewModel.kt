package de.alekseipopov.fooddiary.ui.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.ui.navigation.MainCoordinator
import de.alekseipopov.fooddiary.ui.report.model.Report
import de.alekseipopov.fooddiary.ui.report.model.ReportUiModel
import de.alekseipopov.fooddiary.core.format.unixTimeToDateDdMm
import de.alekseipopov.fooddiary.ui.navigation.NavEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportScreenViewModel(
    private val coordinator: MainCoordinator,
    private val startDate: Long,
    private val endDate: Long,
    private val repository: DayRecordRepository
): ViewModel() {

    val reportRecords: StateFlow<ReportUiModel>
        get() = _reportRecords
    private val _reportRecords = MutableStateFlow(ReportUiModel())

    init {
        _reportRecords.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getReport(startDate, endDate)
                .catch { e ->
                    _reportRecords.update { state -> state.copy(isLoading = false, errorMessage = e.localizedMessage) }
                    Log.e("Exception", e.localizedMessage ?: "")
                }
                .retry(3)
                .collect {
                    val report = Report(
                        startDateString = startDate.unixTimeToDateDdMm(),
                        endDateString = endDate.unixTimeToDateDdMm(),
                        records = it
                    )
                    _reportRecords.update { state -> state.copy(isLoading = false, report = report) }
                }
        }
    }

    fun back() {
        viewModelScope.launch(Dispatchers.Main) {
            coordinator.navigate(NavEvent.Back())
        }
    }
}