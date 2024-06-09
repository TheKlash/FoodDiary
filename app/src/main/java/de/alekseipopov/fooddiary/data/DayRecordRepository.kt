package de.alekseipopov.fooddiary.data

import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.Report
import kotlinx.coroutines.flow.Flow

interface DayRecordRepository {
    suspend fun getDayRecords(): Flow<List<Day>>
    suspend fun getDay(id: Int): Flow<Day>
    //suspend fun getReport(startDate: Long, endDate: Long): Report
    //suspend fun getDetails(dayRecordId: Int): Day

}