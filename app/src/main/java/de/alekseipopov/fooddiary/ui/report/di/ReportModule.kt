package de.alekseipopov.fooddiary.ui.report.di

import de.alekseipopov.fooddiary.ui.report.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    viewModel { parameters -> ReportViewModel(
        repository = get(),
        startDate = parameters.get(),
        endDate = parameters.get()
    ) }
}