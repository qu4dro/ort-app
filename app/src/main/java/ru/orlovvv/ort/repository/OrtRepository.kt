package ru.orlovvv.ort.repository

import androidx.lifecycle.LiveData
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.models.LocationPreview

class OrtRepository(private val ortDB: OrtDatabase) {

    // network

    // database
    suspend fun insertLocation(locationPreview: LocationPreview) =
        ortDB.getOrtDao().insertLocation(locationPreview)

    suspend fun deleteLocation(locationPreview: LocationPreview) =
        ortDB.getOrtDao().deleteLocation(locationPreview)

    fun getAllLocations(): LiveData<List<LocationPreview>> = ortDB.getOrtDao().getAllLocations()
}