package ru.orlovvv.ort.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.repository.LocalRepository
import javax.inject.Inject

@HiltViewModel
class SavedLocationsViewModel @Inject constructor(private val localRepository: LocalRepository) :
    ViewModel() {

    var searchQuery = MutableLiveData("")

    private var _savedLocations = Transformations.switchMap(searchQuery) {
        localRepository.getSavedLocations(it.trim())
    }
    val savedLocations
        get() = _savedLocations

    private val _currentAddressString: MutableLiveData<String> = MutableLiveData()
    val currentAddressString: LiveData<String>
        get() = _currentAddressString

    fun saveLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        localRepository.insertLocation(locationPreview)
    }

    fun deleteLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        localRepository.deleteLocation(locationPreview)
    }

}