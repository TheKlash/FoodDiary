package de.alekseipopov.fooddiary.core.data

import de.alekseipopov.fooddiary.data.model.Course
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.Meal

val testRecordList = mutableListOf(
    Day(
        id = 0,
        time = 1704067200L,
        meals = listOf(
            Meal(
                id = 0,
                time = 1704067740L,
                name = "Breakfast",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Fired eggs"
                    ),
                    Course(
                        id = 1,
                        name = "Coffee"
                    )
                )
            ),
            Meal(
                id = 1,
                time = 1704068040L,
                name = "Lunch",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Roastbeef"
                    ),
                    Course(
                        id = 1,
                        name = "Mashed potatoes"
                    )
                ),
            ),
            Meal(
                id = 2,
                time = 1704068400L,
                name = "Dinner",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Burger"
                    ),
                    Course(
                        id = 1,
                        name = "Fries"
                    ),
                    Course(
                        id = 2,
                        name = "Coke"
                    )
                ),
            ),
        )
    ),
    Day(
        id = 1,
        time = 1704153600L,
        meals = listOf(
            Meal(
                id = 0,
                time = 1704178800L,
                name = "Breakfast",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Tworog mit jam"
                    )
                ),
            ),
            Meal(
                id = 1,
                time = 1704193200L,
                name = "Lunch",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Burger"
                    ),
                    Course(
                        id = 1,
                        name = "Fries"
                    ),
                    Course(
                        id = 2,
                        name = "Coke"
                    )
                ),
            ),
            Meal(
                id = 2,
                time = 1704214800L,
                name = "Poldnik",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Kefir"
                    )
                ),
            ),
            Meal(
                id = 3,
                time = 1704232800L,
                name = "Dinner",
                courses = listOf(
                    Course(
                        id = 0,
                        name = "Caesar salad"
                    )
                ),
            )
        )
    )
)

val testRecord = Day(
    id = 1,
    time = 1704153600L,
    meals = listOf(
        Meal(
            id = 0,
            time = 1704178800L,
            name = "Breakfast",
            courses = listOf(
                Course(
                    id = 0,
                    name = "Tworog mit jam"
                )
            ),
        ),
        Meal(
            id = 1,
            time = 1704193200L,
            name = "Lunch",
            courses = listOf(
                Course(
                    id = 0,
                    name = "Burger"
                ),
                Course(
                    id = 1,
                    name = "Fries"
                ),
                Course(
                    id = 2,
                    name = "Coke"
                )
            ),
        ),
        Meal(
            id = 2,
            time = 1704214800L,
            name = "Poldnik",
            courses = listOf(
                Course(
                    id = 0,
                    name = "Kefir"
                )
            ),
        ),
        Meal(
            id = 3,
            time = 1704232800L,
            name = "Dinner",
            courses = listOf(
                Course(
                    id = 0,
                    name = "Caesar salad"
                )
            ),
        )
    )
)


val testMeal = Meal(
    id = 1,
    time = 1704193200L,
    name = "Lunch",
    courses = listOf(
        Course(
            id = 0,
            name = "Burger"
        ),
        Course(
            id = 1,
            name = "Fries"
        ),
        Course(
            id = 2,
            name = "Coke"
        ),
        Course(
            id = 3,
            name = "Суп из тысячи залуп"
        )
    ),
)
