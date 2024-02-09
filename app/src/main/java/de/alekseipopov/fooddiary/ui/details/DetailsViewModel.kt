package de.alekseipopov.fooddiary.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.domain.DayRecordRepository

class DetailsViewModel(
    val repository: DayRecordRepository
): ViewModel() {

    private val _recordLiveData =  MutableLiveData<DayRecord?>(null)
    val recordLiveData: LiveData<DayRecord?>
        get() = _recordLiveData

    fun getRecord(id: String?) {
        id?.let {
            _recordLiveData.value = repository.getRecord(it)
        }
    }

}