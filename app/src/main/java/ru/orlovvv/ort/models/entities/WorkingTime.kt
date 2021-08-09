package ru.orlovvv.ort.models.entities

data class WorkingTime(
    val closeTime: String,
    val days: String,
    val isClosed: Boolean,
    val openTime: String
)