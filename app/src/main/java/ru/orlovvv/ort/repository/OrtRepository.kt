package ru.orlovvv.ort.repository

import androidx.lifecycle.LiveData
import ru.orlovvv.ort.api.RetrofitInstance
import ru.orlovvv.ort.db.OrtDAO
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.models.Review
import ru.orlovvv.ort.models.ReviewPost
import javax.inject.Inject

class OrtRepository @Inject constructor(private val ortDAO: OrtDAO) {

    // network
    suspend fun getNearbyLocations(lng: Double, lat: Double, maxDistance: Int) =
        RetrofitInstance.api.getNearbyLocations(lng, lat, maxDistance)

    suspend fun addLocation(location: LocationPost) =
        RetrofitInstance.api.addLocation(location)

    suspend fun addReview(review: ReviewPost, locationId: String) =
        RetrofitInstance.api.addReview(review, locationId)

    suspend fun getLocationInfo(id: String) = RetrofitInstance.api.getLocationInfo(id)

    // database
    suspend fun insertLocation(locationPreview: LocationPreview) =
        ortDAO.insertLocation(locationPreview)

    suspend fun deleteLocation(locationPreview: LocationPreview) =
        ortDAO.deleteLocation(locationPreview)

    fun getSavedLocations(searchQuery: String): LiveData<List<LocationPreview>> =
        ortDAO.getAllLocations(searchQuery)


}