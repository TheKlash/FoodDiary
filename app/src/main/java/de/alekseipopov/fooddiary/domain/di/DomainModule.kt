package de.alekseipopov.fooddiary.domain.di

import de.alekseipopov.fooddiary.domain.RecordRepository
import de.alekseipopov.fooddiary.domain.RecordRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<RecordRepository> { RecordRepositoryImpl() }
}