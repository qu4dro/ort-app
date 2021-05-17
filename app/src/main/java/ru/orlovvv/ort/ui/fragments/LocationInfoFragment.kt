package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentLocationInfoBinding
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class LocationInfoFragment : Fragment(R.layout.fragment_location_info) {

    private val args: LocationInfoFragmentArgs by navArgs()

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentLocationInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val location = arguments?.get("location") as LocationPreview

        binding = FragmentLocationInfoBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel

        binding.apply {
            lifecycleOwner = this@LocationInfoFragment
            viewModel = ortViewModel
            locationPreview = location
        }
        return binding.root
    }
}