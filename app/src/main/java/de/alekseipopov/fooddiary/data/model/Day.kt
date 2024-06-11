package de.alekseipopov.fooddiary.data.model

import de.alekseipopov.fooddiary.data.model.entity.DayRecordEntity
import de.alekseipopov.fooddiary.util.unixTimeToDateFull

data class Day(
    val id: Int,
    var time: Long,
    val meals: List<Meal>
) {
    val fullTime: String
        get() = time.unixTimeToDateFull()

    fun toDayRecordEntity(): DayRecordEntity = DayRecordEntity(
        id = this.id,
        date = this.time
    )
}