package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.ui.fragments.nearby.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.viewmodels.SavedLocationsViewModel

@AndroidEntryPoint
class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private val savedLocationsViewModel: SavedLocationsViewModel by activityViewModels()

    private var _binding: FragmentSavedLocationsBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSavedLocationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@SavedLocationsFragment
            viewModel = savedLocationsViewModel
            rvSavedLocations.apply {
                adapter = LocationAdapter()
                setHasFixedSize(true)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}