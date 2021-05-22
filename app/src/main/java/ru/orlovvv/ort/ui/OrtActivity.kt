package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.ActivityOrtBinding
import ru.orlovvv.ort.ui.dialogs.AddLocationDialogFragment
import ru.orlovvv.ort.ui.dialogs.AddReviewDialogFragment
import ru.orlovvv.ort.ui.dialogs.BottomNavigationDrawerFragment

@AndroidEntryPoint
class OrtActivity : AppCompatActivity() {

    val ortViewModel: OrtViewModel by viewModels()
    val locationViewModel: LocationViewModel by viewModels()

    private lateinit var _binding: ActivityOrtBinding
    val binding: ActivityOrtBinding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityOrtBinding.inflate(layoutInflater)

        setContentView(_binding.root)

        setBottomAppBarListeners()
    }

    @MenuRes
    private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.savedLocationsFragment -> R.menu.bottom_app_bar_saved
            R.id.mapsFragment -> R.menu.bottom_app_bar_map
            R.id.locationInfoFragment -> R.menu.bottom_app_bar_location_info
            else -> R.menu.bottom_app_bar_nearby
        }
    }

    private fun showMenu(isFabVisible: Boolean) {
        _binding.apply {
            babMenu.visibility = View.VISIBLE
            babMenu.performShow()
            if (isFabVisible) {
                fab.show()
            } else {
                fab.hide()
            }
        }

    }

    private fun setBottomAppBarForSaved(@MenuRes menuRes: Int) {
        _binding.apply {
            babMenu.replaceMenu(menuRes)
            showMenu(false)
        }
    }

    private fun setBottomAppBarForMaps(@MenuRes menuRes: Int) {
        _binding.apply {
            babMenu.replaceMenu(menuRes)
            showMenu(true)
        }
    }

    private fun setBottomAppBarForNearby(@MenuRes menuRes: Int) {
        _binding.apply {
            fab.apply {
                setImageResource((R.drawable.ic_add_24))
                setOnClickListener {
                    val dialog = AddLocationDialogFragment()
                    dialog.show(supportFragmentManager, "addLocationFragmentDialog")
                }
            }
            babMenu.setFabAlignmentModeAndReplaceMenu(
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER,
                menuRes
            )
            showMenu(true)
        }
    }

    private fun setBottomAppBarForLocationInfo(@MenuRes menuRes: Int) {
        _binding.apply {
            fab.setImageResource((R.drawable.ic_write_review_24))
            babMenu.setFabAlignmentModeAndReplaceMenu(
                BottomAppBar.FAB_ALIGNMENT_MODE_END,
                menuRes
            )
            showMenu(true)
            _binding.fab.setOnClickListener {
                val dialog = AddReviewDialogFragment()
                dialog.show(supportFragmentManager, "addReviewFragmentDialog")
            }
        }
    }

    private fun setBottomAppBarListeners() {

        _binding.babMenu.setNavigationOnClickListener {
            val dialog = BottomNavigationDrawerFragment()
            dialog.show(supportFragmentManager, "navigationDrawerMenu")
        }

        nav_host_fragment.findNavController()
            .addOnDestinationChangedListener { contoller, destination, arguments ->
                when (destination.id) {
                    R.id.nearbyLocationsFragment -> {
                        setBottomAppBarForNearby(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.savedLocationsFragment -> {
                        setBottomAppBarForSaved(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.mapsFragment -> {
                        setBottomAppBarForMaps(getBottomAppBarMenuForDestination(destination))
                    }
                    R.id.locationInfoFragment -> {
                        setBottomAppBarForLocationInfo(getBottomAppBarMenuForDestination(destination))
                    }
                }
            }

    }
}