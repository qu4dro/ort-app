package ru.orlovvv.ort.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.orlovvv.ort.models.LocationPreview

interface Api {

    @GET("/api/locations/")
    suspend fun getNearbyLocations(
        @Query("lng") lng: Double,
        @Query("lat") lat: Double,
        @Query("maxDistance") maxDistance: Int
    ): Response<LocationPreview>
}