package ru.orlovvv.ort.ui.fragments

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_ort.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentLoadingBinding
import ru.orlovvv.ort.ui.LocationViewModel
import ru.orlovvv.ort.ui.OrtViewModel
import ru.orlovvv.ort.util.Constants
import ru.orlovvv.ort.util.LocationUtility
import ru.orlovvv.ort.util.Resource
import timber.log.Timber

class LoadingFragment : Fragment(R.layout.fragment_loading), EasyPermissions.PermissionCallbacks {

    private val ortViewModel: OrtViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var binding: FragmentLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoadingBinding.inflate(inflater)

        requestPermissions()

        return binding.root
    }

    private fun requestLocationUpdates() {
        locationViewModel.locationLiveData.observe(viewLifecycleOwner, Observer {
            Timber.d("${it.lat} ${it.lng}")
            ortViewModel.getNearbyLocationsFromServer(locationViewModel.locationLiveData.value!!)
        })


        waitForResponse()
    }

    private fun waitForResponse() {
        Timber.d("asdasdasdasd}")
        ortViewModel.nearbyLocations.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    nav_host_fragment.findNavController()
                        .navigate(R.id.action_loadingFragment_to_nearbyLocationsFragment)
                }
                is Resource.Loading -> {
                    //
                }
                is Resource.Error -> {
                    response.message.let {
                        //
                    }
                }
            }

        })
    }


    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            requestLocationUpdates()
            return
        }
        else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    Constants.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "You need to accept location permissions",
                    Constants.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                        //place for code for background location
                )
            }
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

