package de.alekseipopov.fooddiary.ui.navigation

interface Coordinator {
    suspend fun navigate(navEvent: NavEvent)
}