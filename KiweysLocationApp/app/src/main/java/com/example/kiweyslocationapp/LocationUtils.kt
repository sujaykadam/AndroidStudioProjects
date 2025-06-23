package com.example.kiweyslocationapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale


class LocationUtils(val context: Context) {

    private val _fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(viewModel: LocationViewModel){
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    val location = LocationData(latitude = it.latitude, longitude = it.longitude)
                    viewModel.updateLocation(location)
                }
            }
        }

        val locationRequest = LocationRequest.Builder(1000L).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build()
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper() )
    }

    fun hasLocationPermission (context: Context) : Boolean {
        val locationCoarsePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        val locationFinePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        return  locationCoarsePermission == PackageManager.PERMISSION_GRANTED
                && locationFinePermission == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeocodeLocation(location: LocationData?): String{
        val geocoder = Geocoder(context, Locale.getDefault())
        val coordinate = LatLng(location?.latitude ?: 0.00, location?.longitude ?: 0.00)
        val addresses: MutableList<Address>? =
            geocoder.getFromLocation(
                coordinate.latitude,
                coordinate.longitude,
                1
            )

        return if (addresses != null && addresses.size > 0) {
            addresses[0].getAddressLine(0) ?: "Unknown Location"
        } else {
            "Unknown Location"
        }
    }
}