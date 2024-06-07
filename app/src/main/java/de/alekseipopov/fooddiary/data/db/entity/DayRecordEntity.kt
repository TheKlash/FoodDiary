package de.alekseipopov.fooddiary.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
data class DayRecordEntity {
	val id: Int,
	val date: String
}
 */

@Entity
data class DayRecordEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "day_record_id")
    val id: Int,
    val date: String
)