package de.alekseipopov.fooddiary.ui.report.di

import de.alekseipopov.fooddiary.ui.report.ReportScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    viewModel { ReportScreenViewModel(get()) }
}