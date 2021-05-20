package ru.orlovvv.ort.api

import retrofit2.Response
import retrofit2.http.*
import ru.orlovvv.ort.models.*

interface Api {

    @GET("/api/locations/")
    suspend fun getNearbyLocations(
        @Query("lng") lng: Double,
        @Query("lat") lat: Double,
        @Query("maxDistance") maxDistance: Int
    ): Response<List<LocationPreview>>

    @GET("/api/locations/{id}")
    suspend fun getLocationInfo(
        @Path("id") id: String
    ): Response<LocationInfo>

    @POST("/api/locations/")
    suspend fun addLocation(@Body locationFullInfo: LocationPost): Response<Void>

    @POST("/api/locations/{id}/reviews")
    suspend fun addReview(@Body review: ReviewPost, @Path("id") id: String): Response<Void>
}