package de.alekseipopov.fooddiary.ui.details

import androidx.lifecycle.ViewModel
import de.alekseipopov.fooddiary.domain.DayRecordRepository

class DetailsViewModel(
    val repository: DayRecordRepository
): ViewModel() {
}