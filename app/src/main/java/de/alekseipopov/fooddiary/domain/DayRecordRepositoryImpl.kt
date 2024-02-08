package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Eating
import de.alekseipopov.fooddiary.util.testRecordList

class DayRecordRepositoryImpl: DayRecordRepository {

    private val dayRecords: List<DayRecord>
        get() = _dayRecords
    private val _dayRecords: MutableList<DayRecord>

    init {
        _dayRecords = testRecordList
    }
    override fun getRecords(): List<DayRecord> {
        return dayRecords
    }

    override fun writeRecord(record: DayRecord): List<DayRecord> {
        _dayRecords.add(record)
        return dayRecords
    }
}