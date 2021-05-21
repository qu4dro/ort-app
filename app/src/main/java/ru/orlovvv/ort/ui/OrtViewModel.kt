package ru.orlovvv.ort.ui

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.ort.models.LocationInfo
import ru.orlovvv.ort.models.LocationPost
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.models.ReviewPost
import ru.orlovvv.ort.repository.OrtRepository
import ru.orlovvv.ort.util.Resource
import java.lang.Exception

class OrtViewModel(private val ortRepository: OrtRepository) : ViewModel() {

    var searchQuery = MutableLiveData("")

    private var _savedLocations = Transformations.switchMap(searchQuery) {
        ortRepository.getSavedLocations(it.trim())
    }
    val savedLocations
        get() = _savedLocations

    private val _nearbyLocations: MutableLiveData<Resource<List<LocationPreview>>> =
        MutableLiveData()
    val nearbyLocations: LiveData<Resource<List<LocationPreview>>>
        get() = _nearbyLocations

    private val _currentLocationInfo: MutableLiveData<Resource<LocationInfo>> = MutableLiveData()
    val currentLocationInfo: LiveData<Resource<LocationInfo>>
        get() = _currentLocationInfo

    private val _currentAddressString: MutableLiveData<String> = MutableLiveData()
    val currentAddressString: LiveData<String>
        get() = _currentAddressString

    init {
        _currentAddressString.value = "Test address string"
    }

    fun getNearbyLocationsFromServer(lng: String, lat: String) = viewModelScope.launch {
        try {
            _nearbyLocations.value = Resource.Loading()
            val response = ortRepository.getNearbyLocations(lng.toDouble(), lat.toDouble(), 10000)
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

    fun getLocationInfo(id: String) = viewModelScope.launch {
        try {
            _currentLocationInfo.value = Resource.Loading()
            val response = ortRepository.getLocationInfo(id)
            _currentLocationInfo.value = handleLocationInfoResponse(response)
        } catch (e: Exception) {

        }
    }

    private fun handleLocationInfoResponse(response: Response<LocationInfo>): Resource<LocationInfo> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        ortRepository.insertLocation(locationPreview)
    }

    fun deleteLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        ortRepository.deleteLocation(locationPreview)
    }

    fun addLocation(location: LocationPost) = viewModelScope.launch {
        ortRepository.addLocation(location)
    }

    fun addReview(review: ReviewPost, locationId: String) = viewModelScope.launch {
        ortRepository.addReview(review, locationId)
    }

}