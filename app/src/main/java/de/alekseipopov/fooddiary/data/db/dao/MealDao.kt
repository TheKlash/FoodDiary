package de.alekseipopov.fooddiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.alekseipopov.fooddiary.data.model.entity.MealEntity
import de.alekseipopov.fooddiary.data.model.entity.MealWithCourses

@Dao
interface MealDao {
    @Query("SELECT * FROM mealentity WHERE day_record_id = :dayRecordId")
    fun selectByDayRecordId(dayRecordId: Int): List<MealEntity>

    @Transaction
    @Query("SELECT * FROM mealentity where time = :time")
    fun getMealsWithCourses(time: Long): List<MealWithCourses>

    @Query("SELECT * FROM mealentity")
    suspend fun getAll(): List<MealEntity>

    @Insert
    suspend fun insert(meal: MealEntity)

    @Update
    suspend fun update(meal: MealEntity)

    @Delete
    suspend fun delete(meal: MealEntity)

}