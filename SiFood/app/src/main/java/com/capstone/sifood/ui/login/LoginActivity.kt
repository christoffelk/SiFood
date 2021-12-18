package com.capstone.sifood.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.capstone.sifood.MainActivity
import com.capstone.sifood.R
import com.capstone.sifood.databinding.ActivityLoginBinding
import com.capstone.sifood.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.tfValueEmail.text.toString().trim()
            val password = binding.tfValuePassword.text.toString().trim()

            // TODO: 18/12/21 membuat validasi email dan password
            userLogin(email, password)
        }

        binding.tvSignUp.setOnClickListener {
            Intent(this, RegisterActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    private fun userLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Intent(this, MainActivity::class.java).let {
                        startActivity(it)
                        finish()
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object{
        private const val TAG = "Login Activity"
    }
}