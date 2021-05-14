package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentSavedLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedLocationsBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel
        locationAdapter = LocationAdapter()

        binding.apply {
            lifecycleOwner = this@SavedLocationsFragment
            viewModel = ortViewModel
            rvSavedLocations.apply {
                layoutManager = LinearLayoutManager(
                    inflater.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = locationAdapter
            }
        }

        return binding.root
    }

}