package ru.orlovvv.ort.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromServices(value: List<String>): String? {
        return value.joinToString()
    }

    @TypeConverter
    fun toServices(value: String): List<String>? {
        return value.split(", ")
    }

}