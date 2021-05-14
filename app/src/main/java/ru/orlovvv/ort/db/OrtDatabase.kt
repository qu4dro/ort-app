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

    companion object {

        @Volatile
        private var instance: OrtDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            OrtDatabase::class.java,
            "ort_db.db"
        ).build()
    }
}