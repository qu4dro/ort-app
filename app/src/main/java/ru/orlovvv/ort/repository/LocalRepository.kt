package ru.orlovvv.ort.repository

import androidx.lifecycle.LiveData
import ru.orlovvv.ort.db.OrtDAO
import ru.orlovvv.ort.models.entities.LocationPreview
import javax.inject.Inject

class LocalRepository @Inject constructor(private val dao: OrtDAO) {

    suspend fun insertLocation(locationPreview: LocationPreview) =
        dao.insertLocation(locationPreview)

    suspend fun deleteLocation(locationPreview: LocationPreview) =
        dao.deleteLocation(locationPreview)

    fun getSavedLocations(searchQuery: String): LiveData<List<LocationPreview>> =
        dao.getAllLocations(searchQuery)

}