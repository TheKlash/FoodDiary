package de.alekseipopov.fooddiary.ui.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.domain.DayRecordRepository
import de.alekseipopov.fooddiary.ui.report.model.Report
import de.alekseipopov.fooddiary.util.unixTimeToDateShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

class ReportScreenViewModel(
    private val repository: DayRecordRepository
): ViewModel() {

    private val _reportRecords = MutableStateFlow<Report?>(null)
    val reportRecords: StateFlow<Report?>
        get() = _reportRecords

    fun getReport(startDate: Long, endDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordsPeriod(startDate, endDate)
                .catch { e ->
                    Log.e("Exception", e.localizedMessage ?: "")
                }
                .retry(3)
                .collect {
                    _reportRecords.emit(
                        Report(
                            startDateString = startDate.unixTimeToDateShort(),
                            endDateString = endDate.unixTimeToDateShort(),
                            records = it
                        )
                    )
                }
        }
    }

}