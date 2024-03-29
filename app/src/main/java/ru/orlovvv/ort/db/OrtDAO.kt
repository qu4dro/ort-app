package ru.orlovvv.ort.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.orlovvv.ort.models.entities.LocationPreview

@Dao
interface OrtDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationPreview: LocationPreview): Long

    @Delete
    suspend fun deleteLocation(locationPreview: LocationPreview)

    @Query(value = "SELECT * FROM locations_preview WHERE name LIKE '%' || :searchQuery || '%' ORDER BY range")
    fun getAllLocations(searchQuery: String): LiveData<List<LocationPreview>>

}