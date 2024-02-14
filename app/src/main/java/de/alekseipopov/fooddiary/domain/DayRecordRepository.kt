package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord
import kotlinx.coroutines.flow.Flow

interface DayRecordRepository {
    fun getRecordsList(): Flow<List<DayRecord>>
    fun writeRecord(record: DayRecord): Boolean
    fun getRecord(recordId: String): Flow<DayRecord?>
}