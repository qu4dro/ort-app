package ru.orlovvv.ort.ui

import android.app.Application
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val _locationLiveData = LocationLiveData(application)
    val locationLiveData
        get() = _locationLiveData

    var gpsEnabled = false

}