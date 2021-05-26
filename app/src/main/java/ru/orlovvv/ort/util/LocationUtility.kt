package ru.orlovvv.ort.util

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import pub.devrel.easypermissions.EasyPermissions
import ru.orlovvv.ort.models.CoordinatesModel
import java.util.*

object LocationUtility {

    fun hasLocationPermissions(context: Context) =

        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )


     fun getAddressString(coordinates: CoordinatesModel, context: Context): String {
         val addresses: List<Address>
         val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            coordinates.lat,
            coordinates.lng,
            1
        )
        val address: String =
            addresses[0].getAddressLine(0)
        val city: String = addresses[0].locality
        val state: String = addresses[0].adminArea
        val country: String = addresses[0].countryName
        val postalCode: String = addresses[0].postalCode
        val knownName: String = addresses[0].featureName
        return address
    }
}