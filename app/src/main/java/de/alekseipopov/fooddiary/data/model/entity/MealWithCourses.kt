package de.alekseipopov.fooddiary.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MealWithCourses(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "meal_id"
    )
    val courses: List<CourseEntity>
)
