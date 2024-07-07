package de.alekseipopov.fooddiary.ui.details.di

import de.alekseipopov.fooddiary.ui.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    viewModel { parameters -> DetailsViewModel(get(), recordId = parameters.get()) }
}