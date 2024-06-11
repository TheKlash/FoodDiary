package de.alekseipopov.fooddiary.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DayRecordWithMealsAndCourses(

    @Embedded val dayRecord: DayRecordEntity,
    @Relation(
        entity = MealEntity::class,
        parentColumn = "id",
        entityColumn = "day_record_id"
    )
    val mealsEntities: List<MealWithCourses>

)
