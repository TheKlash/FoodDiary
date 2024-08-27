package de.alekseipopov.fooddiary.ui.navigation

internal object OverviewScreenRoute: RouteWithNoArgs("overview_screen")
internal object DetailsScreenRoute: RouteWithArgs<Int>("details_screen")
internal object ReportScreenRoute: RouteWithArgs<Pair<Long, Long>>("report_screen")