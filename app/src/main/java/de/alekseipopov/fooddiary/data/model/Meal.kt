package de.alekseipopov.fooddiary.data.model

data class Meal(
    val id: Int,
    val time: Long,
    val name: String,
    val courses: List<Course> = emptyList()
)