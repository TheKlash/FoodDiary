package de.alekseipopov.fooddiary.data.model

import de.alekseipopov.fooddiary.util.unixTimeToDateFull

data class Day(
    val id: Int,
    val time: Long,
    val meals: List<Meal>
) {
    val fullTime: String
        get() = time.unixTimeToDateFull()
}