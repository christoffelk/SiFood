package com.capstone.sifood

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.capstone.sifood.databinding.ActivityMainBinding
import com.capstone.sifood.other.Constant.LATITUDE
import com.capstone.sifood.other.Constant.LOCATION_NAME
import com.capstone.sifood.other.Constant.LONGITUDE
import com.capstone.sifood.other.LocationPicker
import com.capstone.sifood.ui.setting.SettingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var locationPicker: LocationPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        locationPicker = LocationPicker(this)
        println(locationPicker.getLastLocation{ name, long, lat ->
            LOCATION_NAME = name
            LONGITUDE = long
            LATITUDE = lat
        })

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_settings ->
            {
                val intent =Intent(this,SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
    override fun onStart() {
        super.onStart()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when(sharedPreferences.getString("key_dark_mode",""))
        {
            "auto" -> updateTheme(0)
            "off" -> updateTheme(1)
            "on" -> updateTheme(2)
        }
    }
    private fun updateTheme(mode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(mode)
        return true
    }

}