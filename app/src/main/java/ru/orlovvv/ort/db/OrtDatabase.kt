package ru.orlovvv.ort.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.orlovvv.ort.models.entities.LocationPreview

@Database(entities = [LocationPreview::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OrtDatabase : RoomDatabase() {

    abstract fun getOrtDao(): OrtDAO

}