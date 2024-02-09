package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord

interface DayRecordRepository {
    fun getRecords(): List<DayRecord>
    fun writeRecord(record: DayRecord): List<DayRecord>
    fun getRecord(recordId: String): DayRecord?
}