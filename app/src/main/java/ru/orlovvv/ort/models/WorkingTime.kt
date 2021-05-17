package ru.orlovvv.ort.models

data class WorkingTime(
    val closeTime: String,
    val days: String,
    val isClosed: Boolean,
    val openTime: String
)