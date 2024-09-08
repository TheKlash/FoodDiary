package de.alekseipopov.fooddiary.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import de.alekseipopov.fooddiary.core.navigation.composable
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.details.DetailsViewModel
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.overview.OverviewViewModel
import de.alekseipopov.fooddiary.ui.report.ReportScreen
import de.alekseipopov.fooddiary.ui.report.ReportScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
internal fun NavGraphBuilder.overviewScreen() {
    composable(route = OverviewScreenRoute) {
        val viewModel = koinViewModel<OverviewViewModel>()
        OverviewScreen(viewModel = viewModel)
    }
}

internal fun NavGraphBuilder.detailsScreen() {
    composable(route = DetailsScreenRoute) { _, args ->
        val viewModel = koinViewModel<DetailsViewModel> {
            parametersOf(args.id)
        }
        DetailsScreen(viewModel = viewModel)
    }
}

internal fun NavGraphBuilder.reportScreen() {
    composable(route = ReportScreenRoute) { _, args ->
        val viewModel = koinViewModel<ReportScreenViewModel> {
            parametersOf(args.startDate, args.endDate)
        }
        ReportScreen(viewModel = viewModel)
    }
}