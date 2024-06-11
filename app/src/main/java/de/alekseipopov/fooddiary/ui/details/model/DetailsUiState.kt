package de.alekseipopov.fooddiary.ui.details.model

import de.alekseipopov.fooddiary.data.model.Day

data class DetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val record: Day? = null
)