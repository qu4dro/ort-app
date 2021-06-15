package ru.orlovvv.ort.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.orlovvv.ort.util.LocationLiveData
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val _locationLiveData = LocationLiveData(application)
    val locationLiveData
        get() = _locationLiveData

    var gpsEnabled = false

}