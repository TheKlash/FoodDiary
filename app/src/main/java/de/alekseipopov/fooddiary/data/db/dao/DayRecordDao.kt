package de.alekseipopov.fooddiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.alekseipopov.fooddiary.data.model.entity.DayRecordEntity
import de.alekseipopov.fooddiary.data.model.entity.DayRecordWithMealsAndCourses
import kotlinx.coroutines.flow.Flow

@Dao
interface DayRecordDao {
    @Transaction
    @Query("SELECT * FROM dayrecordentity")
    fun getAll(): Flow<List<DayRecordWithMealsAndCourses>>

    @Transaction
    @Query("SELECT * FROM dayrecordentity WHERE id = :id")
    fun getDayRecordWithMeals(id: Int): Flow<DayRecordWithMealsAndCourses>

    @Insert
    suspend fun insert(dayRecord: DayRecordEntity)

    @Update
    suspend fun update(dayRecord: DayRecordEntity)

    @Delete
    suspend fun delete(dayRecord: DayRecordEntity)

}