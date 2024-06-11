package de.alekseipopov.fooddiary.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val FORMAT_DATE_FULL = "EEEE, MMMM dd yyyy"
const val FORMAT_DATE_HH_MM = "HH:mm"
const val FORMAT_DATE_DD_MM = "dd.MM"

private val formatterDateFull = SimpleDateFormat(FORMAT_DATE_FULL, Locale.getDefault())
private val formatterDateHhMm = SimpleDateFormat(FORMAT_DATE_HH_MM, Locale.getDefault())
private val formatterDateDdMm = SimpleDateFormat(FORMAT_DATE_DD_MM, Locale.getDefault())


fun Long.unixTimeToDateFull(): String = formatterDateFull.format(Date(this * 1000))

fun Long.unixTimeToDateHhMm(): String = formatterDateHhMm.format(Date(this * 1000))

fun Long.unixTimeToDateDdMm(): String = formatterDateDdMm.format(Date(this * 1000))
