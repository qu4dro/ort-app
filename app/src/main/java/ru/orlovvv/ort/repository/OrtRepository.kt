package ru.orlovvv.ort.repository

import androidx.lifecycle.LiveData
import ru.orlovvv.ort.api.RetrofitInstance
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.models.LocationPreview

class OrtRepository(private val ortDB: OrtDatabase) {

    // network
    suspend fun getNearbyLocations(lng: Double, lat: Double, maxDistance: Int) =
        RetrofitInstance.api.getNearbyLocations(lng, lat, maxDistance)

    suspend fun addLocation(location: LocationPost) =
        RetrofitInstance.api.addLocation(location)

    suspend fun getLocationInfo(id: String) = RetrofitInstance.api.getLocationInfo(id)

    // database
    suspend fun insertLocation(locationPreview: LocationPreview) =
        ortDB.getOrtDao().insertLocation(locationPreview)

    suspend fun deleteLocation(locationPreview: LocationPreview) =
        ortDB.getOrtDao().deleteLocation(locationPreview)

    fun getSavedLocations(): LiveData<List<LocationPreview>> = ortDB.getOrtDao().getAllLocations()


}