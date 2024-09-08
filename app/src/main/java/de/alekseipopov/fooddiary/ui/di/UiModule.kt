package de.alekseipopov.fooddiary.ui.di

import de.alekseipopov.fooddiary.ui.details.DetailsViewModel
import de.alekseipopov.fooddiary.ui.navigation.MainCoordinator
import de.alekseipopov.fooddiary.ui.overview.OverviewViewModel
import de.alekseipopov.fooddiary.ui.report.ReportScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    single { MainCoordinator() }
    viewModel {
        OverviewViewModel(
            coordinator = get(),
            repository = get()
        )
    }
    viewModel {
        ReportScreenViewModel(
            coordinator = get(),
            startDate = it.get<Long>(),
            endDate = it.get<Long>(),
            repository = get()
        )
    }
    viewModel {
        DetailsViewModel(
            coordinator = get(),
            id = it.get<Int>(),
            repository = get()
        )
    }
}