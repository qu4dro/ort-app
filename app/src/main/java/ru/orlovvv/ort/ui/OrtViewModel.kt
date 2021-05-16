package ru.orlovvv.ort.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.repository.OrtRepository
import ru.orlovvv.ort.util.Resource
import java.lang.Exception

class OrtViewModel(private val ortRepository: OrtRepository) : ViewModel() {

    private var locationsResponse: List<LocationPreview>? = null

    private var _savedLocations = ortRepository.getSavedLocations()
    val savedLocations
        get() = _savedLocations

    private val _nearbyLocations: MutableLiveData<Resource<List<LocationPreview>>> = MutableLiveData()
    val nearbyLocations: LiveData<Resource<List<LocationPreview>>>
        get() = _nearbyLocations


    init {
        getNearbyLocations()
    }

    fun getNearbyLocations() = viewModelScope.launch {
        try {
            Log.d("123", "LOADING: ")
            _nearbyLocations.value = Resource.Loading()
            val response = ortRepository.getNearbyLocations(104.299971, 52.222977, 10000)
            _nearbyLocations.value = handleNearbyLocationsResponse(response)
        } catch (e: Exception) {
//            _nearbyLocations.value = Resource.Success((ArrayList()))
        }

    }

    private fun handleNearbyLocationsResponse(response: Response<List<LocationPreview>>): Resource<List<LocationPreview>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}