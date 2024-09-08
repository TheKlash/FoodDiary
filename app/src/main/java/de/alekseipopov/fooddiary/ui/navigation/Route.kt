package de.alekseipopov.fooddiary.ui.navigation

import de.alekseipopov.fooddiary.core.data.toEncodedJson
import de.alekseipopov.fooddiary.core.data.toJson
import de.alekseipopov.fooddiary.core.navigation.RouteWithArgs
import de.alekseipopov.fooddiary.core.navigation.RouteWithNoArgs

internal object OverviewScreenRoute : RouteWithNoArgs("overview_screen")

internal object DetailsScreenRoute : RouteWithArgs<DetailsScreenRoute.Args>("details_screen") {
    data class Args(val id: Int? = null) {
        override fun toString() = toEncodedJson()
    }
}

internal object ReportScreenRoute : RouteWithArgs<ReportScreenRoute.Args>("report_screen") {
    data class Args(
        val startDate: Long? = null,
        val endDate: Long? = null
    ) {
        override fun toString() = toEncodedJson()
    }
}