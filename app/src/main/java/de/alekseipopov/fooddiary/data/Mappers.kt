package de.alekseipopov.fooddiary.data

import de.alekseipopov.fooddiary.data.model.Course
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.Meal
import de.alekseipopov.fooddiary.data.model.entity.CourseEntity
import de.alekseipopov.fooddiary.data.model.entity.DayRecordWithMealsAndCourses
import de.alekseipopov.fooddiary.data.model.entity.MealWithCourses

fun List<DayRecordWithMealsAndCourses>.toDayList(): List<Day> = this.map { it.toDay() }

fun DayRecordWithMealsAndCourses.toDay() = Day(
    id = this.dayRecord.id,
    time = this.dayRecord.date,
    meals = this.mealsEntities.toMeals()
)

fun List<MealWithCourses>.toMeals(): List<Meal> = this.map { it.toMeal() }

fun MealWithCourses.toMeal(): Meal = Meal(
    id = this.meal.id,
    time = this.meal.time,
    name = this.meal.name,
    courses = this.courses.toCourses()
)

fun List<CourseEntity>.toCourses(): List<Course> = this.map { it.toCourse() }

fun CourseEntity.toCourse(): Course = Course(
    id = this.id,
    name = this.name
)