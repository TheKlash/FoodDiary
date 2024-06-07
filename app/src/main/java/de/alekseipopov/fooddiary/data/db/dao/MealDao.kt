package de.alekseipopov.fooddiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.alekseipopov.fooddiary.data.db.entity.MealEntity

@Dao
interface MealDao {
    @Query("SELECT * FROM mealentity WHERE day_record_id = :dayRecordId")
    suspend fun selectByDayRecordId(dayRecordId: Int): List<MealEntity>

    @Query("SELECT * FROM mealentity")
    suspend fun getAll(): List<MealEntity>

    @Insert
    suspend fun insert(meal: MealEntity): Boolean

    @Update
    suspend fun update(meal: MealEntity): Boolean

    @Delete
    suspend fun delete(meal: MealEntity): Boolean

}