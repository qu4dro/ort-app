package ru.orlovvv.ort.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.ort.models.*
import ru.orlovvv.ort.models.entities.LocationInfo
import ru.orlovvv.ort.models.entities.LocationPreview
import ru.orlovvv.ort.models.post.LocationPost
import ru.orlovvv.ort.models.post.ReviewPost
import ru.orlovvv.ort.repository.NetworkRepository
import ru.orlovvv.ort.util.Resource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NearbyLocationsViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _nearbyLocations: MutableLiveData<Resource<List<LocationPreview>>> =
        MutableLiveData()
    val nearbyLocations: LiveData<Resource<List<LocationPreview>>>
        get() = _nearbyLocations

    private val _currentLocationInfo: MutableLiveData<Resource<LocationInfo>> = MutableLiveData()
    val currentLocationInfo: LiveData<Resource<LocationInfo>>
        get() = _currentLocationInfo

    private val _selectedLocationPreview = MutableLiveData<LocationPreview>()
    val selectedLocationPreview
        get() = _selectedLocationPreview

    fun getNearbyLocationsFromServer(coordinates: CoordinatesModel) = viewModelScope.launch {
        try {
            _nearbyLocations.value = Resource.Loading()
            val response =
                networkRepository.getNearbyLocations(coordinates.lng, coordinates.lat, 10000)
            _nearbyLocations.value = handleNearbyLocationsResponse(response)
        } catch (e: Exception) {
            _nearbyLocations.value = Resource.Error("Cant get nearby locations: ${e.message}")
        }
    }

    fun getLocationInfo(id: String) = viewModelScope.launch {
        try {
            _currentLocationInfo.value = Resource.Loading()
            val response = networkRepository.getLocationInfo(id)
            _currentLocationInfo.value = handleLocationInfoResponse(response)
        } catch (e: Exception) {
            _currentLocationInfo.value = Resource.Error("Cant get location info: ${e.message}")
        }
    }

    fun addLocation(location: LocationPost) = viewModelScope.launch {
        networkRepository.addLocation(location)
    }

    fun addReview(review: ReviewPost, locationId: String) = viewModelScope.launch {
        networkRepository.addReview(review, locationId)
    }

    private fun handleNearbyLocationsResponse(response: Response<List<LocationPreview>>): Resource<List<LocationPreview>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleLocationInfoResponse(response: Response<LocationInfo>): Resource<LocationInfo> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun setSelectedLocationPreview(locationPreview: LocationPreview) {
        _selectedLocationPreview.value = locationPreview
    }

}