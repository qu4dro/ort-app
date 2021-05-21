package ru.orlovvv.ort.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.ActivityOrtBinding
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.repository.OrtRepository
import ru.orlovvv.ort.ui.dialogs.AddLocationDialogFragment
import ru.orlovvv.ort.ui.dialogs.AddReviewDialogFragment
import ru.orlovvv.ort.ui.dialogs.BottomNavigationDrawerFragment
import android.Manifest.permission
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer

class OrtActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 2000

    private lateinit var _binding: ActivityOrtBinding
    val binding: ActivityOrtBinding
        get() = _binding

    private lateinit var _ortViewModel: OrtViewModel
    val ortViewModel: OrtViewModel
        get() = _ortViewModel

    private lateinit var _coordinatesViewModel: CoordinatesViewModel
    val coordinatesViewModel: CoordinatesViewModel
        get() = _coordinatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityOrtBinding.inflate(layoutInflater)

        val ortDatabase = OrtDatabase(this)
        val ortRepository = OrtRepository(ortDatabase)
        val ortViewModelProviderFactory = OrtViewModelProviderFactory(ortRepository)
        _ortViewModel =
            ViewModelProvider(this, ortViewModelProviderFactory).get(OrtViewModel::class.java)
        _coordinatesViewModel = ViewModelProvider(this).get(CoordinatesViewModel::class.java)
        setContentView(_binding.root)

        _binding.babMenu.setNavigationOnClickListener {
            val dialog = BottomNavigationDrawerFragment()
            dialog.show(supportFragmentManager, "navigationDrawerMenu")
        }
        setNavigationListeners()
        prepRequestLocationUpdates()
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

    private fun setBottomAppBarForNearby(@MenuRes menuRes: Int) {
        _binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            fab.setImageResource((R.drawable.ic_add_24))
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.show()
            babMenu.setFabAlignmentModeAndReplaceMenu(
                BottomAppBar.FAB_ALIGNMENT_MODE_CENTER,
                menuRes
            )

            _binding.fab.setOnClickListener {
                val dialog = AddLocationDialogFragment()
                dialog.show(supportFragmentManager, "addLocationFragmentDialog")
            }
        }
    }

    private fun setBottomAppBarForSaved(@MenuRes menuRes: Int) {
        _binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.hide()
        }
    }

    private fun setBottomAppBarForMaps(@MenuRes menuRes: Int) {
        _binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            babMenu.replaceMenu(menuRes)
            babMenu.visibility = View.VISIBLE
            babMenu.performShow()
            fab.show()
        }
    }

    private fun setBottomAppBarForLocationInfo(@MenuRes menuRes: Int) {
        _binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            fab.setImageResource((R.drawable.ic_write_review_24))
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.show()
            _binding.babMenu.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END

            _binding.fab.setOnClickListener {
                val dialog = AddReviewDialogFragment()
                dialog.show(supportFragmentManager, "addReviewFragmentDialog")
            }
        }
    }

    private fun setNavigationListeners() {

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

                    R.id.loadingFragment -> {
                        binding.babMenu.visibility = View.INVISIBLE
                        binding.fab.visibility = View.INVISIBLE
                    }
                }
            }
    }


    //location coordinates code
    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                applicationContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun requestLocationUpdates() {
        coordinatesViewModel.coordinates.observe(this, Observer {
            it.lat
            it.lng
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Check location permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}