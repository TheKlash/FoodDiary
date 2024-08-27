package de.alekseipopov.fooddiary.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainCoordinator(): Coordinator {
    private val _events = MutableSharedFlow<NavEvent>()
    val events = _events.asSharedFlow()

    override suspend fun navigate(navEvent: NavEvent) {
        _events.emit(navEvent)
    }
}