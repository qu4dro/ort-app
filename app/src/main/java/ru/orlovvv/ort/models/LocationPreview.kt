package ru.orlovvv.ort.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locations_preview")
data class LocationPreview(
    @PrimaryKey
    val _id: String,
    val address: String,
    val name: String,
    val range: Double,
    val services: List<String>,
    val stars: Int
) : Serializable