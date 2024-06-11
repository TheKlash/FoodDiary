package de.alekseipopov.fooddiary.ui.report.model

import de.alekseipopov.fooddiary.data.model.Day

data class Report(
    val startDateString: String,
    val endDateString: String,
    val records: List<Day>?
)
