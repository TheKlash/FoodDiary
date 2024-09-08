package de.alekseipopov.fooddiary.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import de.alekseipopov.fooddiary.core.navigation.Route
import de.alekseipopov.fooddiary.core.navigation.RouteWithArgs
import de.alekseipopov.fooddiary.core.navigation.RouteWithNoArgs

sealed class NavEvent {
    class Dialog(): NavEvent()
    class Toast(val text: String): NavEvent()
    class ComposeScreen<T>(val route: Route<T>, val args: T? = null): NavEvent() {
        fun navigate(navHostController: NavHostController, navOptions: NavOptions? = null) {
            when(route) {
                is RouteWithArgs -> route.navigate(navHostController, args, navOptions)
                is RouteWithNoArgs -> route.navigate(navHostController, navOptions)
            }
        }
    }
    class PopTo(val route: String? = null): NavEvent()
    class Back(): NavEvent()
}