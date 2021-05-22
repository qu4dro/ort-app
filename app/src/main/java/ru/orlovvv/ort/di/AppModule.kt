package ru.orlovvv.ort.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.orlovvv.ort.db.OrtDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOrtDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        OrtDatabase::class.java,
        "ort_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideOrtDAO(db: OrtDatabase) = db.getOrtDao()

}