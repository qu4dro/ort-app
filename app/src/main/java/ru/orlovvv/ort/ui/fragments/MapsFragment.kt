package ru.orlovvv.ort.ui.fragments

import android.annotation.SuppressLint
import android.content.res.Resources.NotFoundException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.AndroidEntryPoint
import ru.orlovvv.ort.R
import ru.orlovvv.ort.databinding.FragmentMapsBinding
import ru.orlovvv.ort.ui.LocationViewModel
import ru.orlovvv.ort.ui.OrtViewModel
import timber.log.Timber


@AndroidEntryPoint
class MapsFragment : Fragment(R.layout.fragment_maps) {

    private val ortViewModel: OrtViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var binding: FragmentMapsBinding

    var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapsBinding.inflate(inflater)

        //val rootView: View = inflater.inflate(R.layout.location_fragment, container, false)
        mMapView = binding.map
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView!!.getMapAsync { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            googleMap!!.isMyLocationEnabled = true

            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                val success = googleMap!!.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        context, R.raw.map_style
                    )
                )
                if (!success) {
                    Timber.d("Style parsing failed.")
                }
            } catch (e: NotFoundException) {
                Timber.d(e, "Can't find style. Error: ")
            }

            // For dropping a marker at a point on the Map
//            val sydney = LatLng(-34.0, 151.0)
//            googleMap!!.addMarker(
//                MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description")
//            )

            val startPosition = LatLng(locationViewModel.locationLiveData.value!!.lat, locationViewModel.locationLiveData.value!!.lng)

            val cameraPosition = CameraPosition.Builder().target(startPosition).zoom(12f).build()
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

}