package de.alekseipopov.fooddiary.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DEFAULT_FORMAT_DATE = "EEEE, MMMM dd yyyy"
const val DEFAULT_FORMAT_TIME = "HH:mm"

fun unixTimeToDate(timestamp: Long): String {
    val formatter = SimpleDateFormat(DEFAULT_FORMAT_DATE, Locale.getDefault())
    return formatter.format(Date(timestamp * 1000))
}

fun unixTimeToTime(timestamp: Long): String {
    val formatter = SimpleDateFormat(DEFAULT_FORMAT_TIME, Locale.getDefault())
    return formatter.format(Date(timestamp * 1000))
}
