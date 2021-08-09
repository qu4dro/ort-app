package ru.orlovvv.ort.models.entities

data class Review(
    val _id: String,
    val author: String,
    val date: String,
    val stars: Int,
    val text: String
)