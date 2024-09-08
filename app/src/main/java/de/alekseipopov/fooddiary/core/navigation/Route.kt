package de.alekseipopov.fooddiary.core.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

interface Route<T> {
    val route: String
}

abstract class RouteWithNoArgs(override val route: String) : Route<Nothing> {
    fun navigate(navHostController: NavHostController, navOptions: NavOptions? = null) {
        navHostController.navigate(route, navOptions)
    }
}

abstract class RouteWithArgs<T>(private val path: String) : Route<T> {

    val argumentName: String = "argument"

    override val route = "$path?$argumentName={$argumentName}"

    fun navigate(
        navHostController: NavHostController,
        argument: T?,
        navOptions: NavOptions? = null
    ) {
        val finalRoute = if (argument == null) {
            path
        } else {
            val pathArguments = "$argumentName=$argument"
            "$path?$pathArguments"
        }
        navHostController.navigate(finalRoute, navOptions)
    }
}