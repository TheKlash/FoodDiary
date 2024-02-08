package de.alekseipopov.fooddiary.domain

import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Eating

class DayRecordRepositoryImpl: DayRecordRepository {

    private val dayRecords: List<DayRecord>
        get() = _dayRecords
    private val _dayRecords: MutableList<DayRecord>

    init {
        _dayRecords = mutableListOf(
            DayRecord(
                id = "0",
                date = "2024-01-01",
                eatings = listOf(
                    Eating(
                        id = 0,
                        time = "09:00",
                        title = "Breakfast",
                        desc = "Fired eggs with coffee"
                    ),
                    Eating(
                        id = 1,
                        time = "14:00",
                        title = "Lunch",
                        desc = "Roastbeef with mashed potatoes"
                    ),
                    Eating(
                        id = 2,
                        time = "20:00",
                        title = "Dinner",
                        desc = "Burger with fries and Coke"
                    ),
                )
            ),
            DayRecord(
                id = "1",
                date = "2024-01-02",
                eatings = listOf(
                    Eating(
                        id = 0,
                        time = "07:00",
                        title = "Breakfast",
                        desc = "Tworog mit jam"
                    ),
                    Eating(
                        id = 1,
                        time = "11:00",
                        title = "Lunch",
                        desc = "`Lunch at McDonalds: burger, fries and coke"
                    ),
                    Eating(
                        id = 2,
                        time = "17:00",
                        title = "Poldnik",
                        desc = "Kefir"
                    ),
                    Eating(
                        id = 3,
                        time = "22:00",
                        title = "Dinner",
                        desc = "Caesar salad"
                    )
                )
            )
        )
    }
    override fun getRecords(): List<DayRecord> {
        return dayRecords
    }

    override fun writeRecord(record: DayRecord): List<DayRecord> {
        _dayRecords.add(record)
        return dayRecords
    }
}