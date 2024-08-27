package de.alekseipopov.fooddiary.ui.navigation

sealed class NavEvent {
    class Dialog(): NavEvent()
    class Toast(val text: String): NavEvent()
    class ComposeScreen<T>(val argument: T): NavEvent()
    class PopTo(val route: String? = null): NavEvent()
    class Back(): NavEvent()
}