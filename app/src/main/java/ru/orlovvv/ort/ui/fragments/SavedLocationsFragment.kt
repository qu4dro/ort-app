package ru.orlovvv.ort.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentSavedLocationsBinding
import ru.orlovvv.ort.ui.OrtActivity
import ru.orlovvv.ort.ui.OrtViewModel

class SavedLocationsFragment : Fragment(R.layout.fragment_saved_locations) {

    private lateinit var ortViewModel: OrtViewModel
    private lateinit var binding: FragmentSavedLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedLocationsBinding.inflate(inflater)
        ortViewModel = (activity as OrtActivity).ortViewModel
        locationAdapter = LocationAdapter()

        binding.apply {
            lifecycleOwner = this@SavedLocationsFragment
            viewModel = ortViewModel
            rvSavedLocations.apply {
                layoutManager = LinearLayoutManager(
                    inflater.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = locationAdapter
            }
        }

        setSearchView()

        return binding.root
    }

    private fun setSearchView() {

        val searchView = (activity as OrtActivity).binding.babMenu.menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ortViewModel.searchQuery.value = newText
//                ortViewModel.getSortOrUpdateStickers()
                return true
            }

        })
    }

}