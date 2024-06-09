package de.alekseipopov.fooddiary.ui.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.ui.report.model.Report
import de.alekseipopov.fooddiary.ui.report.model.ReportUiModel
import de.alekseipopov.fooddiary.util.unixTimeToDateDdMm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportScreenViewModel(
    private val repository: DayRecordRepository
): ViewModel() {

    val reportRecords: StateFlow<ReportUiModel>
        get() = _reportRecords
    private val _reportRecords = MutableStateFlow(ReportUiModel())

    fun getReport(startDate: Long, endDate: Long) {
        /* _reportRecords.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordsPeriod(startDate, endDate)
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
        } */
    }

}