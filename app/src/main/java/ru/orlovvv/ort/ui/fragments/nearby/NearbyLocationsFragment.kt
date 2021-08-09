package ru.orlovvv.ort.ui.fragments.nearby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.viewmodels.CoordinatesViewModel
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel
import ru.orlovvv.ort.viewmodels.OrtViewModel

@AndroidEntryPoint
class NearbyLocationsFragment : Fragment(R.layout.fragment_nearby_locations) {

    private val coordinatesViewModel: CoordinatesViewModel by activityViewModels()
    private val nearbyLocationsViewModel: NearbyLocationsViewModel by activityViewModels()

    private val locationAdapter: LocationAdapter = LocationAdapter()

    private var _binding: FragmentNearbyLocationsBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNearbyLocationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@NearbyLocationsFragment
            viewModel = nearbyLocationsViewModel
            rvNearbyLocations.apply {
                layoutManager = LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = locationAdapter
            }
            swipeRefresh.setOnRefreshListener {
                nearbyLocationsViewModel.getNearbyLocationsFromServer(coordinatesViewModel.locationLiveData.value!!)
                swipeRefresh.isRefreshing = false
            }
        }


        locationAdapter.setOnItemClickListener {
            nearbyLocationsViewModel.getLocationInfo(it._id)

            val bundle = Bundle().apply {
                putSerializable("location", it)
            }
            findNavController().navigate(
                R.id.action_nearbyLocationsFragment_to_locationInfoFragment,
                bundle
            )
        }

    }

    override fun onResume() {
        super.onResume()
        coordinatesViewModel.locationLiveData.observe(viewLifecycleOwner, Observer {})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
