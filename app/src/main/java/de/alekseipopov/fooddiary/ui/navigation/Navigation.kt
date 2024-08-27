package de.alekseipopov.fooddiary.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.report.ReportScreen

@ExperimentalMaterial3Api
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        composable(route = Screen.Overview.route) {
            OverviewScreen(navigateToDetails = {
                navController.navigate(
                    Screen.Details.createRoute(it)
                )
            }, navigateToReport = { startDate, endDate ->
                navController.navigate(
                    Screen.Report.createRoute(startDate, endDate)
                )
            })
        }
        composable(
            route = Screen.Details.route, arguments = Screen.Details.navArguments
        ) {
            DetailsScreen(
                onBackPressed = { navController.popUpTo(Screen.Overview.route) },
                recordId = it.arguments?.getInt(Screen.Details.recordId) ?: -1
            )
        }
        composable(
            route = Screen.Report.route, arguments = Screen.Report.navArguments
        ) {
            ReportScreen(
                onBackPressed = { navController.popUpTo(Screen.Overview.route) },
                startDate = it.arguments?.getLong(Screen.Report.startDate) ?: 0L,
                endDate = it.arguments?.getLong(Screen.Report.endDate) ?: 0L
            )
        }
    }
}

fun NavController.popUpTo(destination: String) = navigate(destination) {
    popUpTo(graph.findStartDestination().id) {
        saveState = true
    }
    restoreState = true
}
