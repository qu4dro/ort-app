package ru.orlovvv.ort.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.ort.R
import ru.orlovvv.ort.adapters.LocationAdapter
import ru.orlovvv.ort.databinding.FragmentNearbyLocationsBinding
import ru.orlovvv.ort.ui.LocationViewModel
import ru.orlovvv.ort.ui.OrtViewModel
import ru.orlovvv.ort.util.Constants
import ru.orlovvv.ort.util.LocationUtility
import timber.log.Timber

@AndroidEntryPoint
class NearbyLocationsFragment : Fragment(R.layout.fragment_nearby_locations),
    EasyPermissions.PermissionCallbacks {

    private val ortViewModel: OrtViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var binding: FragmentNearbyLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNearbyLocationsBinding.inflate(inflater)
        locationAdapter = LocationAdapter()

        binding.apply {
            lifecycleOwner = this@NearbyLocationsFragment
            viewModel = ortViewModel
            rvNearbyLocations.apply {
                layoutManager = LinearLayoutManager(
                    inflater.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = locationAdapter
            }
        }

        requestPermissions()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAdapter.setOnItemClickListener {
            ortViewModel.getLocationInfo(it._id)

            val bundle = Bundle().apply {
                putSerializable("location", it)
            }
            findNavController().navigate(
                R.id.action_nearbyLocationsFragment_to_locationInfoFragment,
                bundle
            )
        }
    }

    private fun requestLocationUpdates() {
        locationViewModel.locationLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("${it.lat} ${it.lng}")
        })
//        ortViewModel.getNearbyLocationsFromServer(locationViewModel.locationLiveData.value!!)

    }


    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            requestLocationUpdates()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions",
                Constants.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        requestLocationUpdates()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
