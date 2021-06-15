package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

@AndroidEntryPoint
class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private val ortViewModel: OrtViewModel by activityViewModels()
    private val locationAdapter: LocationAdapter = LocationAdapter()

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
            viewModel = ortViewModel
            rvSavedLocations.apply {
                layoutManager = LinearLayoutManager(
                    view.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = locationAdapter
            }
        }

        setSearchView()
    }

    private fun setSearchView() {

        val searchView =
            (activity as OrtActivity).binding.babMenu.menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ortViewModel.searchQuery.value = newText
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}