package de.alekseipopov.fooddiary.data

import de.alekseipopov.fooddiary.data.model.Day
import kotlinx.coroutines.flow.Flow

interface DayRecordRepository {
    suspend fun getDayRecords(): Flow<List<Day>>
    suspend fun getDay(id: Int): Flow<Day>
    suspend fun getReport(startDate: Long, endDate: Long): Flow<List<Day>>
    suspend fun createNewDay(date: Long): Int
    suspend fun updateDay(day: Day)
    suspend fun deleteDay(day: Day)
}