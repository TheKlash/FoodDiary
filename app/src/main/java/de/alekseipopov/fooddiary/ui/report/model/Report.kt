package de.alekseipopov.fooddiary.ui.report.model

import de.alekseipopov.fooddiary.data.model.DayRecord

data class Report(
    val startDateString: String,
    val endDateString: String,
    val records: List<DayRecord>?
)
