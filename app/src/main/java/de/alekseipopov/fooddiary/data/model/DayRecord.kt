package de.alekseipopov.fooddiary.data.model

data class DayRecord(
    val id: String,
    val date: Long,
    val meals: List<Meal>?
)