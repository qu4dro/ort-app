package ru.orlovvv.ort.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.orlovvv.ort.models.*
import ru.orlovvv.ort.repository.OrtRepository
import ru.orlovvv.ort.util.Resource
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class OrtViewModel @Inject constructor(private val ortRepository: OrtRepository) : ViewModel() {

    var searchQuery = MutableLiveData("")

    private var _savedLocations = Transformations.switchMap(searchQuery) {
        ortRepository.getSavedLocations(it.trim())
    }
    val savedLocations
        get() = _savedLocations


    private val _currentAddressString: MutableLiveData<String> = MutableLiveData()
    val currentAddressString: LiveData<String>
        get() = _currentAddressString

    init {

        _currentAddressString.value = "Test address string"
    }


    fun saveLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        ortRepository.insertLocation(locationPreview)
    }

    fun deleteLocation(locationPreview: LocationPreview) = viewModelScope.launch {
        ortRepository.deleteLocation(locationPreview)
    }


}