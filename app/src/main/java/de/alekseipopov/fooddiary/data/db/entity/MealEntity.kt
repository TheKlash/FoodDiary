package de.alekseipopov.fooddiary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
data class MealEntity {
	val id: Int,
	val dayRecordId: Int,
	val time: String,
	val title: String?,
}
 */

@Entity(foreignKeys = [
    ForeignKey(
        entity = DayRecordEntity::class,
        parentColumns = ["day_record_id"],
        childColumns = ["day_record_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class MealEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meal_id")
    val id: Int,
    @ColumnInfo(name = "day_record_id")
    val dayRecordId: Int,
)