package de.alekseipopov.fooddiary.ui.report

class ReportArguments(
    val startDate: Long,
    val endDate: Long
) {
    companion object {
        const val ARGUMENT_START_DATE = "start_date"
        const val ARGUMENT_END_DATE = "end_date"
    }

    override fun toString(): String = "$ARGUMENT_START_DATE=$startDate&$ARGUMENT_END_DATE=$endDate"
}
