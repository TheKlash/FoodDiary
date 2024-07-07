package de.alekseipopov.fooddiary.ui.details.model

import de.alekseipopov.fooddiary.ui.base.UiState

sealed class DetailsUiEvents {
    class ShowEditDateDialog() : DetailsUiEvents()
    class HideEditDateDialog() : DetailsUiEvents()
    class ShowDeleteDialog(): DetailsUiEvents()
    class HideDeleteDialog(): DetailsUiEvents()
    class NavigateBack(): DetailsUiEvents()
}
