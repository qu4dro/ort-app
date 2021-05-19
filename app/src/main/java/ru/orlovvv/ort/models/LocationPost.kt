package ru.orlovvv.ort.models

import com.squareup.moshi.Json

data class LocationPost(
    @Json(name = "name")
    val name: String,
    @Json(name = "address")
    val address: String,
    @Json(name = "services")
    val services: String,
    @Json(name = "lng")
    val lng: Double,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "daysWeekDays")
    val daysWeekDays: String,
    @Json(name = "openTimeWeekDays")
    val openTimeWeekDays: String = " ",
    @Json(name = "closeTimeWeekDays")
    val closeTimeWeekDays: String = " ",
    @Json(name = "isClosedWeekDays")
    val isClosedWeekDays: Boolean = false,
    @Json(name = "daysWeekend")
    val daysWeekend: String = " ",
    @Json(name = "openTimeWeekend")
    val openTimeWeekend: String = " ",
    @Json(name = "closeTimeWeekend")
    val closeTimeWeekend: String = " ",
    @Json(name = "isClosedWeekend")
    val isClosedWeekend: Boolean = false

) {
    @Json(name = "_id")
    val _id: String? = null
}