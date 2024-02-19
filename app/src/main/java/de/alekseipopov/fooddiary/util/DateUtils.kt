package de.alekseipopov.fooddiary.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DEFAULT_FORMAT_DATE = "EEEE, MMMM dd yyyy"
const val DEFAULT_FORMAT_TIME = "HH:mm"
const val DATE_SHORT = "dd.MM"

fun Long.unixTimeToDate(): String {
    val formatter = SimpleDateFormat(DEFAULT_FORMAT_DATE, Locale.getDefault())
    return formatter.format(Date(this * 1000))
}

fun Long.unixTimeToTime(): String {
    val formatter = SimpleDateFormat(DEFAULT_FORMAT_TIME, Locale.getDefault())
    return formatter.format(Date(this * 1000))
}

fun Long.unixTimeToDateShort(): String {
    val formatter = SimpleDateFormat(DATE_SHORT, Locale.getDefault())
    return formatter.format(Date(this * 1000))
}
