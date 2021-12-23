package com.capstone.sifood.ui.nointernet

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.capstone.sifood.ui.main.MainActivity
import com.capstone.sifood.R

class NoInternetActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet)

        val btn: Button = findViewById(R.id.btn_contact)

        btn.setOnClickListener {
            if (isOnline(this)){
                Intent(this, MainActivity::class.java).let {
                    startActivity(it)
                    finish()
                }
            } else {
                Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capa =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capa != null) {
                if (capa.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capa.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capa.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
}