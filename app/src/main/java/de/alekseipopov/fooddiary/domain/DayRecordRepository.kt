package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord
import kotlinx.coroutines.flow.Flow

interface DayRecordRepository {
    suspend fun getRecordsList(): Flow<List<DayRecord>?>
    suspend fun writeRecord(record: DayRecord): Boolean
    suspend fun getRecord(recordId: String): Flow<DayRecord?>
    suspend fun getRecordsPeriod(startDate: Long, endDate: Long): Flow<List<DayRecord>?>
}