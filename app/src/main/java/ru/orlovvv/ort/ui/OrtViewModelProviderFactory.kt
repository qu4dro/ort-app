package ru.orlovvv.ort.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.orlovvv.ort.repository.OrtRepository

class OrtViewModelProviderFactory(private val ortRepository: OrtRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrtViewModel(ortRepository) as T
    }
}