package de.alekseipopov.fooddiary.ui.overview.di

import de.alekseipopov.fooddiary.ui.overview.OverviewViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val overviewModule = module {
    viewModel { OverviewViewModel(get()) }
}