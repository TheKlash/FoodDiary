package de.alekseipopov.fooddiary.data.di

import androidx.room.Room
import de.alekseipopov.fooddiary.data.DayRecordRepository
import de.alekseipopov.fooddiary.data.DayRecordRepositoryImpl
import de.alekseipopov.fooddiary.data.db.Database
import org.koin.dsl.module

val dataModule = module {
    single<Database> {
        Room
            .databaseBuilder(
                context = get(),
                klass = Database::class.java,
                name = Database.DATABASE_NAME
            )
            .createFromAsset("${Database.DATABASE_NAME}.db")
            .build()
    }

    single<DayRecordRepository> {
        DayRecordRepositoryImpl(
            database = get()
        )
    }
}