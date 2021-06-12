package ru.orlovvv.ort.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String? {
    val pattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    val outputFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)

    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)

}