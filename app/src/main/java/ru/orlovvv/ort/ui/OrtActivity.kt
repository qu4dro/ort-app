package ru.orlovvv.ort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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
        binding.bnMenu.setupWithNavController(nav_host_fragment.findNavController())

        val ortDatabase = OrtDatabase(this)
        val ortRepository = OrtRepository(ortDatabase)
        val ortViewModelProviderFactory = OrtViewModelProviderFactory(ortRepository)
        _ortViewModel =
            ViewModelProvider(this, ortViewModelProviderFactory).get(OrtViewModel::class.java)

        setContentView(binding.root)

        setNavigationListeners()
    }

    private fun setNavigationListeners() {
        nav_host_fragment.findNavController()
            .addOnDestinationChangedListener { contoller, destination, arguments ->
                when (destination.id) {
                    R.id.mapsFragment -> {
                        binding.btnAddLocation.show()

                    }
                    R.id.nearbyLocationsFragment -> {
                        binding.btnAddLocation.show()

                    }
                    else -> binding.btnAddLocation.hide()
                }

            }

        binding.btnAddLocation.setOnClickListener {
            val currentDestination = nav_host_fragment.findNavController().currentDestination
            if (currentDestination?.id == R.id.nearbyLocationsFragment) nav_host_fragment.findNavController()
                .navigate(R.id.action_nearbyLocationsFragment_to_addLocationFragment)
            if (currentDestination?.id == R.id.mapsFragment) nav_host_fragment.findNavController()
                .navigate(R.id.action_mapsFragment_to_addLocationFragment)
        }
    }
}