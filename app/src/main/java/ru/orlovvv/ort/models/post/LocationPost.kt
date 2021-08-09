package ru.orlovvv.ort.models.post

import com.squareup.moshi.Json

data class LocationPost(
    val name: String,
    val address: String,
    val services: String,
    val lng: Double,
    val lat: Double,
    val daysWeekDays: String = " ",
    val openTimeWeekDays: String = " ",
    val closeTimeWeekDays: String = " ",
    val isClosedWeekDays: Boolean = false,
    val daysWeekend: String = " ",
    val openTimeWeekend: String = " ",
    val closeTimeWeekend: String = " ",
    val isClosedWeekend: Boolean = false

) {
    @Json(name = "_id")
    val _id: String? = null
}