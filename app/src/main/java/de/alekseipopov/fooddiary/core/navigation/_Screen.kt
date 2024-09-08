package de.alekseipopov.fooddiary.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.alekseipopov.fooddiary.core.data.fromJson

inline fun <reified T, R : RouteWithArgs<T>> NavGraphBuilder.composable(
    route: R,
    defaultArgument: T? = null,
    deepLinks: List<NavDeepLink> = emptyList(),
    crossinline content: @Composable (NavBackStackEntry, T) -> Unit
) {
    val params: (Bundle?, String) -> T? = { bundle, key ->
        //bundle?.getString(key)?.let(Uri::decode)?.fromJson<T>()
        val a = bundle?.getString(key)
        val b = a?.let(Uri::decode)
        val c = b?.fromJson<T>()
        c
    }

    val arguments = listOf(
        navArgument(route.argumentName) {
            type = NavType.StringType
            if (defaultArgument != null) {
                defaultValue = defaultArgument.toString()
            }
        }
    )

    composable(
        route = route.route,
        arguments = arguments,
        deepLinks = deepLinks
    ) {
        @Suppress("IMPLICIT_CAST_TO_ANY")
        val screenParam: T = when (T::class) {
            String::class -> it.arguments?.getString(route.argumentName)
            Int::class -> it.arguments?.getInt(route.argumentName)
            Double::class -> it.arguments?.getDouble(route.argumentName)
            Float::class -> it.arguments?.getFloat(route.argumentName)
            else -> params(it.arguments, route.argumentName)
        } as T
        content(it, screenParam)
    }
}

inline fun <R: RouteWithNoArgs> NavGraphBuilder.composable(
    route: R,
    deepLinks: List<NavDeepLink> = emptyList(),
    crossinline content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = route.route,
        deepLinks = deepLinks
    ) {
        content(it)
    }
}