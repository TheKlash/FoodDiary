package de.alekseipopov.fooddiary.ui.overview

sealed class OverviewUiEvents() {
    class ShowReportDatePickerDialog() : OverviewUiEvents()
    class HideReportDatePickerDialog() : OverviewUiEvents()
    class ShowNewEntryDialog() : OverviewUiEvents()
    class HideNewEntryDialog() : OverviewUiEvents()

}
