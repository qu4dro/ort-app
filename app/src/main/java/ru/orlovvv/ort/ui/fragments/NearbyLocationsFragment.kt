package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

@AndroidEntryPoint
class NearbyLocationsFragment : Fragment(R.layout.fragment_nearby_locations) {

    private val ortViewModel : OrtViewModel by activityViewModels()


    private lateinit var binding: FragmentNearbyLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNearbyLocationsBinding.inflate(inflater)
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

        ortViewModel.getNearbyLocationsFromServer()

        return binding.root
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
