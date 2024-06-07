package de.alekseipopov.fooddiary.data.di

import androidx.room.Room
import de.alekseipopov.fooddiary.data.db.Database
import org.koin.dsl.module

val dataModule = module {
    single<Database> {
        Room.databaseBuilder(
            context = get(),
            klass = Database::class.java,
            name = "food_diary_db"
        ).build()
    }
}