package ru.orlovvv.ort.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CoordinatesViewModel(application: Application) : AndroidViewModel(application) {

    private val _coordinates = CoordinatesLiveData(application)
    val coordinates
        get() = _coordinates
}