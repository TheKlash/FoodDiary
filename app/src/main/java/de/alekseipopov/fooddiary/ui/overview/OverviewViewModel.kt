package de.alekseipopov.fooddiary.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.domain.DayRecordRepository

class OverviewViewModel(
    private val repository: DayRecordRepository
): ViewModel() {

    val recordsListLiveData : LiveData<List<DayRecord>?>
        get() = _recordsListLiveData
    private val _recordsListLiveData = MutableLiveData<List<DayRecord>?>(null)

    fun getRecords() {
        _recordsListLiveData.value = repository.getRecordsList()
    }


}