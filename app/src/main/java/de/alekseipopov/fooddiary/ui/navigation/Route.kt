package de.alekseipopov.fooddiary.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

interface Route {
    val path: String
}

abstract class RouteWithNoArgs(override val path: String): Route {

    fun navigate(
        navHostController: NavHostController,
        navOptions: NavOptions? = null
    ) {
        navHostController.navigate("$path?", navOptions)
    }
}

abstract class RouteWithArgs<T>(val pathName: String): Route {

    override val path = "$pathName?argument"
    val argumentName: String = "argument"

    fun navigate(
        navHostController: NavHostController,
        argument: T,
        navOptions: NavOptions? = null
    ) {
        navHostController.navigate("$pathName?$argumentName=$argument", navOptions)
    }
}