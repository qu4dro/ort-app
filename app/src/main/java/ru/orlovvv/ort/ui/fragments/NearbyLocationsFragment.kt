package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class NearbyLocationsFragment : Fragment(R.layout.fragment_nearby_locations) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentNearbyLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNearbyLocationsBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("locationPreview", it)
            }
            findNavController().navigate(
                R.id.action_nearbyLocationsFragment_to_locationInfoFragment,
                bundle
            )
        }
    }

}
