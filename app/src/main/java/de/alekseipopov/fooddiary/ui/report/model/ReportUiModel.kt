package de.alekseipopov.fooddiary.ui.report.model

data class ReportUiModel(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val report: Report? = null
)
