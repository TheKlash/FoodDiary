package de.alekseipopov.fooddiary.data.model

data class DayRecord(
    val id: String,
    val date: String?,
    val meals: List<Meal>?
)