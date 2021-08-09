package ru.orlovvv.ort.models.entities

data class LocationInfo(
    val _id: String,
    val address: String,
    val coordinates: List<Double>,
    val name: String,
    val reviews: List<Review>,
    val services: List<String>,
    val stars: Int,
    val workingTime: List<WorkingTime>
)