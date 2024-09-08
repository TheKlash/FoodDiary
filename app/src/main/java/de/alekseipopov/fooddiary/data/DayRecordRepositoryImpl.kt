package de.alekseipopov.fooddiary.data

import de.alekseipopov.fooddiary.data.db.Database
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.entity.DayRecordEntity
import de.alekseipopov.fooddiary.data.model.entity.DayRecordWithMealsAndCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DayRecordRepositoryImpl(
    private val database: Database
) : DayRecordRepository {
    override suspend fun getDayRecords(): Flow<List<Day>> =
        database.dayRecordDao()
            .getAll()
            .map(List<DayRecordWithMealsAndCourses>::toDayList)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getDay(id: Int): Flow<Day> =
        database.dayRecordDao()
            .getDayRecordWithMeals(id)
            .map(DayRecordWithMealsAndCourses::toDay)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getReport(startDate: Long, endDate: Long): Flow<List<Day>> =
        database.dayRecordDao()
            .getDayRecordWithMeals(startDate, endDate)
            .map(List<DayRecordWithMealsAndCourses>::toDayList)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun createNewDay(date: Long): Int = withContext(Dispatchers.IO) {
        val newDay = DayRecordEntity(id = 0, date = date)
        return@withContext database.dayRecordDao().insert(newDay).toInt()
    }

    override suspend fun updateDay(day: Day) = withContext(Dispatchers.IO) {
        database.dayRecordDao().update(day.toDayRecordEntity())
    }

    override suspend fun deleteDay(day: Day) = withContext(Dispatchers.IO) {
        database.dayRecordDao().delete(day.toDayRecordEntity())
    }
}
