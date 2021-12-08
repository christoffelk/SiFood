package com.capstone.sifood.other

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*
import android.app.Activity
import android.content.ComponentCallbacks
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class LocationPicker(private val context: Context) {

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context as Activity)


    private fun CheckPermission():Boolean {
        if(
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false

    }

    private fun RequestPermission(){
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION),
            Constant.PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(callbacks: (String, String, String)->Unit){
        if(CheckPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location: Location? = task.result
                    if(location == null){
                        Toast.makeText(context as Activity,"Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
                    }else{
                        val longitude = location.longitude.toString()
                        val latitude = location.latitude.toString()
                        getLocationName(location.latitude,location.longitude)?.let {
                            callbacks(it,longitude,latitude)
                        }
                    }
                }
            }else{
                Toast.makeText(context as Activity,"Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("SetTextI18n")
    private fun getLocationName(lat: Double, long: Double): String? {
        return try {
            val geoCoder = Geocoder(context, Locale.getDefault())
            val Adress = geoCoder.getFromLocation(lat,long,1)
            Adress[0].adminArea.toString()
        } catch (e:Exception){
            "Bali"
        }


    }
}