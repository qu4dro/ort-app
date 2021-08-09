package ru.orlovvv.ort.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentLoadingBinding
import ru.orlovvv.ort.models.CoordinatesModel
import ru.orlovvv.ort.viewmodels.CoordinatesViewModel
import ru.orlovvv.ort.viewmodels.OrtViewModel
import ru.orlovvv.ort.util.Constants
import ru.orlovvv.ort.util.LocationUtility
import ru.orlovvv.ort.util.Resource
import ru.orlovvv.ort.viewmodels.NearbyLocationsViewModel


class LoadingFragment : Fragment(R.layout.fragment_loading), EasyPermissions.PermissionCallbacks {

    private val nearbyLocationsViewModel: NearbyLocationsViewModel by activityViewModels()
    private val coordinatesViewModel: CoordinatesViewModel by activityViewModels()

    private var _binding: FragmentLoadingBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGpsEnabled()
        requestPermissions()

    }

    private fun requestLocationUpdates() {
        coordinatesViewModel.locationLiveData.apply {
            observe(viewLifecycleOwner, object : Observer<CoordinatesModel> {

                override fun onChanged(t: CoordinatesModel?) {
                    value?.let {
                        nearbyLocationsViewModel.getNearbyLocationsFromServer(it)
                        waitForResponse()
                    }
                    removeObserver(this)
                }
            })
        }
    }

    private fun waitForResponse() {
        nearbyLocationsViewModel.nearbyLocations.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    binding.loadingIndicator.visibility = View.GONE
                    findNavController()
                        .navigate(R.id.action_loadingFragment_to_nearbyLocationsFragment)
                }
                is Resource.Loading -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    val snackbar = makeSnack(response.message.toString())
                    binding.loadingIndicator.visibility = View.GONE
                    snackbar.show()
                }
            }

        })
    }


    private fun requestPermissions() {
        if (LocationUtility.hasLocationPermissions(requireContext())) {
            requestLocationUpdates()
            return
        } else {
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

    private fun checkGpsEnabled() {
        val lm: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        try {
            coordinatesViewModel.gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {

        }

        if (!coordinatesViewModel.gpsEnabled) {
            makeCheckLocationSnack()
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

    private fun makeSnack(message: String): Snackbar {
        return Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Go offline") {
            findNavController().navigate(R.id.action_loadingFragment_to_nearbyLocationsFragment)
        }
    }

    private fun makeCheckLocationSnack() {
        val snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            "Location disabled",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Enable") {
            requireContext().startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            )
        }

        snackbar.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}