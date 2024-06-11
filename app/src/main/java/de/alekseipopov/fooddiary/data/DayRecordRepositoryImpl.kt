package de.alekseipopov.fooddiary.data

import android.provider.SyncStateContract.Helpers.insert
import de.alekseipopov.fooddiary.data.db.Database
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.entity.DayRecordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
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
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getDay(id: Int): Flow<Day> =
        database.dayRecordDao()
            .getDayRecordWithMeals(id)
            .map {
                it.toDay()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getReport(startDate: Long, endDate: Long): Flow<List<Day>> =
        database.dayRecordDao()
            .getDayRecordWithMeals(startDate, endDate)
            .map {
                it.toDayList()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun createNewDay(date: Long): Long {
        val newDay = DayRecordEntity(id = 0, date = date)
        return database.dayRecordDao().insert(newDay)
    }
}
