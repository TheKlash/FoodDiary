package de.alekseipopov.fooddiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.alekseipopov.fooddiary.data.model.entity.CourseEntity

@Dao
interface CourseDao {
    @Query("SELECT * FROM courseentity WHERE meal_id = :mealId")
    fun selectByMealId(mealId: Int): List<CourseEntity>

    @Query("SELECT * FROM courseentity")
    fun getAll(): List<CourseEntity>

    @Insert
    suspend fun insert(course: CourseEntity)

    @Update
    suspend fun update(course: CourseEntity)

    @Delete
    suspend fun delete(course: CourseEntity)
}