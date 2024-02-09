package de.alekseipopov.fooddiary.util

import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Meal

val testRecordList = mutableListOf(
    DayRecord(
        id = "0",
        date = "2024-01-01",
        meals = listOf(
            Meal(
                id = 0,
                time = "09:00",
                title = "Breakfast",
                courses = listOf("Fired eggs", "Coffee")
            ),
            Meal(
                id = 1,
                time = "14:00",
                title = "Lunch",
                courses = listOf("Roastbeef", "Mashed potatoes"),
            ),
            Meal(
                id = 2,
                time = "20:00",
                title = "Dinner",
                courses = listOf("Burger", "Fries", "Coke"),
            ),
        )
    ),
    DayRecord(
        id = "1",
        date = "2024-01-02",
        meals = listOf(
            Meal(
                id = 0,
                time = "07:00",
                title = "Breakfast",
                courses = listOf("Tworog mit jam"),
            ),
            Meal(
                id = 1,
                time = "11:00",
                title = "Lunch",
                courses = listOf("Burger", "Fries", "Coke"),
            ),
            Meal(
                id = 2,
                time = "17:00",
                title = "Poldnik",
                courses = listOf("Kefir"),
            ),
            Meal(
                id = 3,
                time = "22:00",
                title = "Dinner",
                courses = listOf("Caesar salad"),
            )
        )
    )
)

val testRecord = DayRecord(
    id = "1",
    date = "2024-01-02",
    meals = listOf(
        Meal(
            id = 0,
            time = "07:00",
            title = "Breakfast",
            courses = listOf("Tworog mit jam"),
        ),
        Meal(
            id = 1,
            time = "11:00",
            title = "Lunch",
            courses = listOf("Burger", "Fries", "Coke"),
        ),
        Meal(
            id = 2,
            time = "17:00",
            title = "Poldnik",
            courses = listOf("Kefir"),
        ),
        Meal(
            id = 3,
            time = "22:00",
            title = "Dinner",
            courses = listOf("Caesar salad"),
        )
    )
)

val testMeal = Meal(
    id = 1,
    time = "11:00",
    title = "Lunch",
    courses = listOf("Burger", "Fries", "Coke"),
)
