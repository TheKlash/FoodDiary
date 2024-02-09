package de.alekseipopov.fooddiary.data.model

data class Meal(
    val id: Int,
    val time: String?,
    val title: String,
    val courses: List<String> = emptyList()
)