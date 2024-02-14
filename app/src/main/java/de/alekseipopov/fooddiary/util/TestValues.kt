package de.alekseipopov.fooddiary.util

import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Meal

val testRecordList = mutableListOf(
    DayRecord(
        id = "0",
        date = 1704067200L,
        meals = listOf(
            Meal(
                id = 0,
                time = 1704067740L,
                title = "Breakfast",
                courses = listOf("Fired eggs", "Coffee")
            ),
            Meal(
                id = 1,
                time = 1704068040L,
                title = "Lunch",
                courses = listOf("Roastbeef", "Mashed potatoes"),
            ),
            Meal(
                id = 2,
                time = 1704068400L,
                title = "Dinner",
                courses = listOf("Burger", "Fries", "Coke"),
            ),
        )
    ),
    DayRecord(
        id = "1",
        date = 1704153600L,
        meals = listOf(
            Meal(
                id = 0,
                time = 1704178800L,
                title = "Breakfast",
                courses = listOf("Tworog mit jam"),
            ),
            Meal(
                id = 1,
                time = 1704193200L,
                title = "Lunch",
                courses = listOf("Burger", "Fries", "Coke"),
            ),
            Meal(
                id = 2,
                time = 1704214800L,
                title = "Poldnik",
                courses = listOf("Kefir"),
            ),
            Meal(
                id = 3,
                time = 1704232800L,
                title = "Dinner",
                courses = listOf("Caesar salad"),
            )
        )
    )
)

val testRecord = DayRecord(
    id = "1",
    date = 1704153600L,
    meals = listOf(
        Meal(
            id = 0,
            time = 1704178800L,
            title = "Breakfast",
            courses = listOf("Tworog mit jam"),
        ),
        Meal(
            id = 1,
            time = 1704193200L,
            title = "Lunch",
            courses = listOf("Burger", "Fries", "Coke"),
        ),
        Meal(
            id = 2,
            time = 1704214800L,
            title = "Poldnik",
            courses = listOf("Kefir"),
        ),
        Meal(
            id = 3,
            time = 1704232800L,
            title = "Dinner",
            courses = listOf("Caesar salad"),
        )
    )
)


val testMeal = Meal(
    id = 1,
    time = 1704193200L,
    title = "Lunch",
    courses = listOf("Burger", "Fries", "Coke", "Суп из тысячи залуп"),
)
