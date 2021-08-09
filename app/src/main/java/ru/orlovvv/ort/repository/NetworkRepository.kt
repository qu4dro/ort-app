package ru.orlovvv.ort.repository

import ru.orlovvv.ort.api.Api
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.models.ReviewPost
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: Api) {

    suspend fun getNearbyLocations(lng: Double, lat: Double, maxDistance: Int) =
        api.getNearbyLocations(lng, lat, maxDistance)

    suspend fun addLocation(location: LocationPost) =
        api.addLocation(location)

    suspend fun addReview(review: ReviewPost, locationId: String) =
        api.addReview(review, locationId)

    suspend fun getLocationInfo(id: String) = api.getLocationInfo(id)

}