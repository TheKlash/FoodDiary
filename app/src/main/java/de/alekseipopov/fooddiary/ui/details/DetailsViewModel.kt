package de.alekseipopov.fooddiary.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.domain.DayRecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailsViewModel(
    val repository: DayRecordRepository
) : ViewModel() {

    val record: StateFlow<DayRecord?>
        get() = _record
    private val _record = MutableStateFlow<DayRecord?>(null)

    fun getRecord(id: String?) {
        id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getRecord(id)
                    .catch { exception ->
                        Log.e("Exception!", exception.localizedMessage ?: "")
                    }
                    .collect {
                        _record.emit(it)
                    }
            }
        }
    }

}