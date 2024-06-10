package de.alekseipopov.fooddiary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import de.alekseipopov.fooddiary.data.db.dao.CourseDao
import de.alekseipopov.fooddiary.data.db.dao.DayRecordDao
import de.alekseipopov.fooddiary.data.db.dao.MealDao
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.data.model.entity.CourseEntity
import de.alekseipopov.fooddiary.data.model.entity.DayRecordEntity
import de.alekseipopov.fooddiary.data.model.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [DayRecordEntity::class, MealEntity::class, CourseEntity::class], version = 1)
abstract class Database: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "food_diary_db"
    }

    abstract fun dayRecordDao(): DayRecordDao
    abstract fun mealDao(): MealDao
    abstract fun courseDao(): CourseDao
}