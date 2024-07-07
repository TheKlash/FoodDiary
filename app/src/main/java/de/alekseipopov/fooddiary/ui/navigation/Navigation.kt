package de.alekseipopov.fooddiary.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.details.DetailsViewModel
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.report.ReportScreen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterial3Api
@Composable
fun Navigation(navController: NavHostController) {
    NavHost (
        navController = navController,
        startDestination = Screen.Overview.route
    ) {
        composable(route = Screen.Overview.route) {
            OverviewScreen (
                navigateToDetails = { navController.navigate(Screen.Details.createRoute(it)) },
                navigateToReport = { startDate, endDate ->
                    navController.navigate(Screen.Report.createRoute(startDate, endDate))
                },
                viewModel = koinViewModel()
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = Screen.Details.navArguments
        ) {
            val recordId = it.arguments?.getInt(Screen.Details.recordId) ?: -1
            val viewModel: DetailsViewModel = koinViewModel(parameters = { parametersOf(recordId) })
            DetailsScreen(
                navigateBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.Report.route,
            arguments = Screen.Report.navArguments
        ) {
            ReportScreen(
                navigateBack = { navController.navigateUp() },
                startDate = it.arguments?.getLong(Screen.Report.startDate) ?: 0L,
                endDate = it.arguments?.getLong(Screen.Report.endDate) ?: 0L
            )
        }
    }
}