package de.alekseipopov.fooddiary.data.model

data class Record(
    val id: String,
    val date: String?,
    val eatings: List<Eating>?
)