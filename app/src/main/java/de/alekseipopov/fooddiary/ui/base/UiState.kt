package de.alekseipopov.fooddiary.ui.base

sealed class UiState<out T> {
    class Loading<T>: UiState<T>()
    data class Result<T>(val data: T): UiState<T>()

    data class Error<T>(val throwable: Throwable): UiState<Nothing>()

}

fun <T> T.toUiState() = UiState.Result(this)

fun <T: Throwable> T.toUiState() = UiState.Error<T>(this)