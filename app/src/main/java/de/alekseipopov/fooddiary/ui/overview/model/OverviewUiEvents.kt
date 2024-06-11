package de.alekseipopov.fooddiary.ui.overview.model

sealed class OverviewUiEvents() {
    class ShowReportDatePickerDialog() : OverviewUiEvents()
    class HideReportDatePickerDialog() : OverviewUiEvents()
    class ShowNewEntryDialog() : OverviewUiEvents()
    class HideNewEntryDialog() : OverviewUiEvents()
    data class ShowNewDay(val id: Long): OverviewUiEvents()

}
