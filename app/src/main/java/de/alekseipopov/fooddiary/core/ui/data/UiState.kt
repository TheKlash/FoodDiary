package de.alekseipopov.fooddiary.core.ui.data

sealed class UiState<out T> {
    class Loading<Nothing>: UiState<Nothing>()
    data class Result<T>(val data: T): UiState<T>()

    data class Error<Nothing>(val throwable: Throwable): UiState<Nothing>()

}

fun <T> T.toUiState() = UiState.Result(this)

fun <T: Throwable> T.toUiState() = UiState.Error<T>(this)