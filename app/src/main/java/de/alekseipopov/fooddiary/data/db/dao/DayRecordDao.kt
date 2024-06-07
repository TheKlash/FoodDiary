package de.alekseipopov.fooddiary.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.alekseipopov.fooddiary.data.db.entity.DayRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayRecordDao {
    @Query("SELECT * FROM dayrecordentity ")
    suspend fun getAll(): Flow<DayRecordEntity>

    @Insert
    suspend fun insert(dayRecord: DayRecordEntity): Boolean

    @Update
    suspend fun update(dayRecord: DayRecordEntity): Boolean

    @Delete
    suspend fun delete(dayRecord: DayRecordEntity): Boolean

}