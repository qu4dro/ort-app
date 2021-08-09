package ru.orlovvv.ort.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentLocationInfoBinding
import ru.orlovvv.ort.models.entities.LocationPreview
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel

@AndroidEntryPoint
class LocationInfoFragment : Fragment(R.layout.fragment_location_info) {

    private val nearbyLocationsViewModel: NearbyLocationsViewModel by activityViewModels()

    private val reviewAdapter: ReviewAdapter = ReviewAdapter()
    private lateinit var location: LocationPreview
    private var _binding: FragmentLocationInfoBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        location = arguments?.get("location") as LocationPreview

        _binding = FragmentLocationInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@LocationInfoFragment
            viewModel = nearbyLocationsViewModel
            locationPreview = location
            rvReviewsBlock.apply {
                layoutManager = LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = reviewAdapter
            }

            swipeRefresh.setOnRefreshListener { nearbyLocationsViewModel.getLocationInfo(location._id)
                swipeRefresh.isRefreshing = false
            }

            nearbyLocationsViewModel.setSelectedLocationPreview(location)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}