package ru.orlovvv.ort.models.post

import com.squareup.moshi.Json

data class ReviewPost(
    @Json(name = "author")
    val author: String,
    @Json(name = "text")
    val text: String,
    @Json(name = "stars")
    val stars: String,
)