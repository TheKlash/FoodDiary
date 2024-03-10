package de.alekseipopov.fooddiary.ui.details.model

sealed class DetailsUiEvents {
    class ShowEditDateDialog() : DetailsUiEvents()
    class HideEditDateDialog() : DetailsUiEvents()
}
