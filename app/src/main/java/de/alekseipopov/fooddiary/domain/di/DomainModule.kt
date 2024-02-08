package de.alekseipopov.fooddiary.domain.di

import de.alekseipopov.fooddiary.domain.DayRecordRepository
import de.alekseipopov.fooddiary.domain.DayRecordRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<DayRecordRepository> { DayRecordRepositoryImpl() }
}