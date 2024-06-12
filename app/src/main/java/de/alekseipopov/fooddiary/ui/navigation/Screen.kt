package de.alekseipopov.fooddiary.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Overview : Screen(
        route = "overview"
    ) {
        fun createRoute() = "overview"
    }

    data object Details : Screen(
        route = "details/{recordId}",
        navArguments = listOf(
            navArgument("recordId") { type = NavType.IntType }
        )
    ) {
        const val recordId = "recordId"
        fun createRoute(recordId: Int) = "details/${recordId}"
    }

    data object Report : Screen(
        route = "report/{startDate}&{endDate}",
        navArguments = listOf(
            navArgument("startDate") { type = NavType.LongType },
            navArgument("endDate") { type = NavType.LongType }
        )
    ) {
        const val startDate = "startDate"
        const val endDate  = "endDate"
        fun createRoute(startDate: Long, endDate: Long) = "report/${startDate}&${endDate}"
    }
}