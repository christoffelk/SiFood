package com.capstone.sifood.other

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class LocationPicker(private val context: Context) {

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context as Activity)


    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            Constant.PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(): LiveData<List<Double>> {
        val result = MutableLiveData<List<Double>>()
        if (checkPermission()) {
            if (isLocationEnabled()) {
                try {
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                        val location: Location? = task.result
                        val koordinat = ArrayList<Double>()
                        if (location == null) {
                            Toast.makeText(
                                context as Activity,
                                "Please Turn on Your device Location",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val longitude = location.longitude
                            val latitude = location.latitude
                            koordinat.add(latitude)
                            koordinat.add(longitude)
                            println("Koordinat: $koordinat")
                            result.value = koordinat
                        }
                    }
                } catch (e: Exception) {
                    println(e)
                }
                return result
            } else {
                Toast.makeText(
                    context as Activity,
                    "Please Turn on Your device Location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            requestPermission()
        }
        val temp = ArrayList<Double>()
        temp.addAll(listOf(3.3369733, 99.3423367))
        result.value = temp
        println("tempt: $temp")
        return result
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun getLocationName(lat: Double, long: Double): LiveData<String> {
        val locationName = MutableLiveData<String>()
        return try {
            val geoCoder = Geocoder(context)
            val adress = geoCoder.getFromLocation(lat, long, 1)
            locationName.value = adress[0].adminArea.toString()
            locationName
        } catch (e: Exception) {
            locationName.value = "Jawa Timur"
            locationName
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: LocationPicker? = null

        fun getInstance(context: Context): LocationPicker =
            instance ?: synchronized(this)
            {
                instance ?: LocationPicker(context)
            }
    }
}