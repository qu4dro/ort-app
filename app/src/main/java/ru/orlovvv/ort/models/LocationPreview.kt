package ru.orlovvv.ort.models

data class LocationPreview(
    val _id: String,
    val address: String,
    val name: String,
    val range: Int,
    val services: List<String>,
    val stars: Int
)