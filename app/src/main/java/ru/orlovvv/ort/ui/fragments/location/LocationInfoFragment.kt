package ru.orlovvv.ort.ui.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentLocationInfoBinding
import ru.orlovvv.ort.models.LocationPreview
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

@AndroidEntryPoint
class LocationInfoFragment : Fragment(R.layout.fragment_location_info) {

    private val ortViewModel: OrtViewModel by activityViewModels()
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
            viewModel = ortViewModel
            locationPreview = location
            rvReviewsBlock.apply {
                layoutManager = LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = reviewAdapter
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}