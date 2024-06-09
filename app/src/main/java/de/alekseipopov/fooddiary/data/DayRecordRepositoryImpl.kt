package de.alekseipopov.fooddiary.data

import de.alekseipopov.fooddiary.data.db.Database
import de.alekseipopov.fooddiary.data.model.Day
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DayRecordRepositoryImpl(
    private val database: Database
) : DayRecordRepository {
    override suspend fun getDayRecords(): Flow<List<Day>> =
        database.dayRecordDao()
            .getAll()
            .map {
                it.toDayList()
            }.flowOn(Dispatchers.IO)

    override suspend fun getDay(id: Int): Flow<Day> =
        database.dayRecordDao()
            .getDayRecordWithMeals(id)
            .map {
                it.toDay()
            }.flowOn(Dispatchers.IO)
}
