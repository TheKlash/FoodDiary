package de.alekseipopov.fooddiary.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.domain.DayRecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val repository: DayRecordRepository
) : ViewModel() {

    val recordsList: StateFlow<List<DayRecord>?>
        get() = _recordsList.asStateFlow()
    private var _recordsList = MutableStateFlow<List<DayRecord>?>(null)

    fun getRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecordsList()
                .catch { exception ->
                    Log.e("Exception!", exception.localizedMessage ?: "")
                }
                .collect {
                    _recordsList.emit(it)
                }
        }
    }

}