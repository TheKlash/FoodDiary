package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Meal
import de.alekseipopov.fooddiary.util.testRecordList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class DayRecordRepositoryImpl : DayRecordRepository {

    private val dayRecords: List<DayRecord>
        get() = _dayRecords
    private val _dayRecords: MutableList<DayRecord>

    init {
        _dayRecords = testRecordList
    }

    override suspend fun getRecordsList(): Flow<List<DayRecord>?> = flow {
        emit(dayRecords)
    }.flowOn(Dispatchers.IO)

    override suspend fun writeRecord(record: DayRecord): Boolean {
        _dayRecords.add(record)
        return true
    }

    override suspend fun getRecord(recordId: String): Flow<DayRecord?> = flow {
        emit(dayRecords.find { it.id == recordId })
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecordsPeriod(startDate: Long, endDate: Long): Flow<List<DayRecord>?> = flow {
        emit(
            dayRecords.filter { it.date in startDate..endDate }
        )
    }.flowOn(Dispatchers.IO)
}