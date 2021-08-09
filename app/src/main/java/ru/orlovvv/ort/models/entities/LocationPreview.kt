package ru.orlovvv.ort.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locations_preview")
data class LocationPreview(
    @PrimaryKey
    val _id: String = "",
    val address: String = "",
    val name: String = "",
    val coordinates: List<String> = listOf(),
    val range: Double = 0.0,
    val services: List<String> = listOf(),
    val stars: Int = 0
) : Serializable