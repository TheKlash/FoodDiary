package de.alekseipopov.fooddiary.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = DayRecordEntity::class,
        parentColumns = ["id"],
        childColumns = ["day_record_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class MealEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "day_record_id")
    val dayRecordId: Int,
    val name: String,
    val time: Long
)