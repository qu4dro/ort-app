package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.adapters.ReviewAdapter
import ru.orlovvv.ort.databinding.FragmentLocationInfoBinding
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class LocationInfoFragment : Fragment(R.layout.fragment_location_info) {

    private val args: LocationInfoFragmentArgs by navArgs()

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentLocationInfoBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var location: LocationPreview

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        location = arguments?.get("location") as LocationPreview

        reviewAdapter = ReviewAdapter()

        binding = FragmentLocationInfoBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel

        binding.apply {
            lifecycleOwner = this@LocationInfoFragment
            viewModel = ortViewModel
            locationPreview = location
            rvReviewsBlock.apply {
                layoutManager = LinearLayoutManager(
                    inflater.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = reviewAdapter
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as OrtActivity).binding.fab.setOnClickListener {

        }

        (activity as OrtActivity).binding.babMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.back -> findNavController().navigateUp()
                R.id.save -> {
                    ortViewModel.saveLocation(location)
                    true
                }
                else -> true
            }
        }
    }
}