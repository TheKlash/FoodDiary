package de.alekseipopov.fooddiary.ui.overview

import androidx.lifecycle.ViewModel
import de.alekseipopov.fooddiary.domain.DayRecordRepository

class OverviewViewModel(
    val repository: DayRecordRepository
): ViewModel() {

}