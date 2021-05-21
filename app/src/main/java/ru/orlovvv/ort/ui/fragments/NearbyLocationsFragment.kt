package ru.orlovvv.ort.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.CoordinatesViewModel
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class NearbyLocationsFragment : Fragment(R.layout.fragment_nearby_locations) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var coordinatesViewModel: CoordinatesViewModel
    private lateinit var binding: FragmentNearbyLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNearbyLocationsBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel
        coordinatesViewModel = (activity as OrtActivity).coordinatesViewModel
        locationAdapter = LocationAdapter()

        binding.apply {
            lifecycleOwner = this@NearbyLocationsFragment
            viewModel = ortViewModel
            rvNearbyLocations.apply {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAdapter.setOnItemClickListener {

            ortViewModel.getLocationInfo(it._id)

            val bundle = Bundle().apply {
                putSerializable("location", it)
            }
            findNavController().navigate(
                R.id.action_nearbyLocationsFragment_to_locationInfoFragment,
                bundle
            )
        }
    }
}
