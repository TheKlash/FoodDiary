package de.alekseipopov.fooddiary.ui.overview.model

import de.alekseipopov.fooddiary.data.model.Day

data class OverviewUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recordList: List<Day>? = null,
    val showDatePickerDialog: Boolean = false
)
