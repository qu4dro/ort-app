package ru.orlovvv.ort.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.orlovvv.ort.models.LocationPreview

@Database(entities = [LocationPreview::class], version = 1)
@TypeConverters(Converters::class)
abstract class OrtDatabase : RoomDatabase() {

    abstract fun getOrtDao(): OrtDAO

}