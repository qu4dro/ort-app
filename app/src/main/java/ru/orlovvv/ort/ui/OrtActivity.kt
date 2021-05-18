package ru.orlovvv.ort.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.MenuRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.animation.AnimationUtils.lerp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.math.MathUtils
import kotlinx.android.synthetic.main.activity_ort.*
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.ActivityOrtBinding
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.repository.OrtRepository

class OrtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrtBinding
    private lateinit var _ortViewModel: OrtViewModel
    val ortViewModel: OrtViewModel
        get() = _ortViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrtBinding.inflate(layoutInflater)

        val ortDatabase = OrtDatabase(this)
        val ortRepository = OrtRepository(ortDatabase)
        val ortViewModelProviderFactory = OrtViewModelProviderFactory(ortRepository)
        _ortViewModel =
            ViewModelProvider(this, ortViewModelProviderFactory).get(OrtViewModel::class.java)

        setContentView(binding.root)

        binding.babMenu.setNavigationOnClickListener {
        }

        setNavigationListeners()
    }

    @MenuRes
    private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.savedLocationsFragment -> R.menu.bottom_app_bar_saved
            R.id.mapsFragment -> R.menu.bottom_app_bar_map
            else -> R.menu.bottom_app_bar_nearby
        }
    }

    private fun setBottomAppBarForNearby(@MenuRes menuRes: Int) {
        binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.show()
        }
    }

    private fun setBottomAppBarForSaved(@MenuRes menuRes: Int) {
        binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.hide()
        }
    }

    private fun setBottomAppBarForMaps(@MenuRes menuRes: Int) {
        binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            babMenu.visibility = View.VISIBLE
            babMenu.replaceMenu(menuRes)
            babMenu.performShow()
            fab.show()
        }
    }

    private fun setBottomAppBarForLocationInfo() {
        binding.run {
            fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            babMenu.performHide()
            fab.hide()
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
                        setBottomAppBarForLocationInfo()
                    }
                }

            }
    }

}