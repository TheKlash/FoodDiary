package de.alekseipopov.fooddiary.ui.overview.model

import de.alekseipopov.fooddiary.data.model.DayRecord

data class OverviewUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recordList: List<DayRecord>? = null,
    val showDatePickerDialog: Boolean = false
)
