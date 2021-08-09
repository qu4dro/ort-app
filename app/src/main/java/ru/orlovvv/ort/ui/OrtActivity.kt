package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.ActivityOrtBinding
import ru.orlovvv.ort.ui.dialogs.AddLocationDialogFragment
import ru.orlovvv.ort.ui.dialogs.AddReviewDialogFragment
import ru.orlovvv.ort.ui.dialogs.BottomNavigationDrawerFragment
import ru.orlovvv.ort.viewmodels.CoordinatesViewModel
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel
import ru.orlovvv.ort.viewmodels.OrtViewModel
import timber.log.Timber

@AndroidEntryPoint
class OrtActivity : AppCompatActivity() {

    val ortViewModel: OrtViewModel by viewModels()
    private val nearbyLocationsViewModel: NearbyLocationsViewModel by viewModels()
    val coordinatesViewModel: CoordinatesViewModel by viewModels()

    private var _binding: ActivityOrtBinding? = null
    val binding: ActivityOrtBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityOrtBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setBottomAppBarListeners()
    }

    @MenuRes
    private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.savedLocationsFragment -> R.menu.bottom_app_bar_saved
            R.id.mapsFragment -> R.menu.bottom_app_bar_map
            R.id.locationInfoFragment -> R.menu.bottom_app_bar_location_info
            R.id.nearbyLocationsFragment -> R.menu.bottom_app_bar_nearby
            else -> R.menu.bottom_app_bar_nearby
        }
    }

    private fun showMenu(isFabVisible: Boolean) {
        binding.run {
            babMenu.visibility = View.VISIBLE
            babMenu.performShow()
            if (isFabVisible) {
                fab.show()
            } else {
                fab.hide()
            }
        }
    }

    private fun hideMenu() {
        binding.run {
            babMenu.visibility = View.GONE
            fab.visibility = View.GONE
        }
    }

    private fun setBottomAppBarForSaved(@MenuRes menuRes: Int) {
        binding.run {
            babMenu.replaceMenu(menuRes)
            showMenu(false)
        }

        val searchView =
            binding.babMenu.menu.findItem(R.id.search).actionView as SearchView

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

    private fun setBottomAppBarForLoading() {
        hideMenu()
    }

    private fun setBottomAppBarForMaps(@MenuRes menuRes: Int) {
        binding.run {
            fab.run {
                setImageResource((R.drawable.ic_add_24))
                setOnClickListener {
                    Toast.makeText(this@OrtActivity, "Button in progress", Toast.LENGTH_LONG).show()
                }
            }
            babMenu.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            babMenu.replaceMenu(menuRes)
            showMenu(true)
        }
    }

    private fun setBottomAppBarForNearby(@MenuRes menuRes: Int) {
        binding.run {
            fab.run {
                setImageResource((R.drawable.ic_add_24))
                setOnClickListener {
                    val dialog = AddLocationDialogFragment()
                    dialog.show(supportFragmentManager, "addLocationFragmentDialog")
                }
            }
            babMenu.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            babMenu.replaceMenu(menuRes)
            showMenu(true)
        }
    }

    private fun setBottomAppBarForLocationInfo(@MenuRes menuRes: Int) {
        binding.run {
            fab.setImageResource((R.drawable.ic_write_review_24))
            babMenu.setFabAlignmentModeAndReplaceMenu(
                BottomAppBar.FAB_ALIGNMENT_MODE_END,
                menuRes
            )
            showMenu(true)
            fab.setOnClickListener {
                val dialog = AddReviewDialogFragment()
                dialog.show(supportFragmentManager, "addReviewFragmentDialog")
            }
        }

        binding.babMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.back -> {
                    onBackPressedDispatcher.onBackPressed()
                    true
                }
                R.id.save -> {
//                    ortViewModel.apply {
//                        saveLocation(currentLocationPreview!!)
//                    }
                    true
                }
                else -> true
            }
        }
    }

    private fun setBottomAppBarListeners() {

        binding.babMenu.setNavigationOnClickListener {
            val dialog = BottomNavigationDrawerFragment()
            dialog.show(supportFragmentManager, "navigationDrawerMenu")
        }
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        navController
            .addOnDestinationChangedListener { contoller, destination, arguments ->
                when (destination.id) {
                    R.id.nearbyLocationsFragment -> {
                        Timber.d("Nearby")
                        setBottomAppBarForNearby(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.savedLocationsFragment -> {
                        Timber.d("Saved")
                        setBottomAppBarForSaved(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.mapsFragment -> {
                        Timber.d("Maps")
                        setBottomAppBarForMaps(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.locationInfoFragment -> {
                        Timber.d("Loc info")
                        setBottomAppBarForLocationInfo(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.loadingFragment -> {
                        Timber.d("Loading")
                        setBottomAppBarForLoading()
                    }
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}