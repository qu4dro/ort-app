package ru.orlovvv.ort.db

import androidx.room.TypeConverter
import ru.orlovvv.ort.models.LocationPreview

class Converters {

    @TypeConverter
    fun fromServices(value: List<String>): String? {
        return value.toString()
    }

    @TypeConverter
    fun toServices(value: String): List<String>? {
        return value.split(",").map { it.trim() }
    }

}