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
    @Query("SELECT * FROM dayrecordentity")
    suspend fun getAll(): Flow<List<DayRecordWithMealsAndCourses>>

    @Transaction
    @Query("SELECT * FROM dayrecordentity WHERE id = id")
    suspend fun getDayRecordWithMeals(id: Int): Flow<DayRecordWithMealsAndCourses>

    @Insert
    suspend fun insert(dayRecord: DayRecordEntity): Boolean

    @Update
    suspend fun update(dayRecord: DayRecordEntity): Boolean

    @Delete
    suspend fun delete(dayRecord: DayRecordEntity): Boolean

}