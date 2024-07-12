package de.alekseipopov.fooddiary.ui.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.ui.base.UiState
import de.alekseipopov.fooddiary.ui.report.model.Report
import de.alekseipopov.fooddiary.util.unixTimeToDateDdMm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ReportViewModel(
    private val repository: DayRecordRepository,
    startDate: Long,
    endDate: Long
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Report>>(UiState.Loading())
    val uiState: StateFlow<UiState<Report>>
        get() = _uiState
    init {
        getReport(startDate, endDate)
    }

    private fun getReport(startDate: Long, endDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getReport(startDate, endDate)
                .catch { exception ->
                    _uiState.value = UiState.Error(exception)
                    Log.e("Exception", exception.localizedMessage ?: "")
                }
                .collect {
                    val report = Report(
                        startDateString = startDate.unixTimeToDateDdMm(),
                        endDateString = endDate.unixTimeToDateDdMm(),
                        days = it
                    )
                    _uiState.value = UiState.Result(report)
                }
        }
    }

}