package de.alekseipopov.fooddiary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = MealEntity::class,
        parentColumns = ["meal_id"],
        childColumns = ["meal_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "meal_id")
    val mealId: Int
)