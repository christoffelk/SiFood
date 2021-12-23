package com.capstone.sifood.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.sifood.R
import com.capstone.sifood.ui.login.LoginActivity
import com.capstone.sifood.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if(currentUser != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)
    }
}