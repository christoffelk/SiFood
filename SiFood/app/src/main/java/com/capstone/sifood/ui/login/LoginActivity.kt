package com.capstone.sifood.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sifood.databinding.ActivityLoginBinding
import com.capstone.sifood.ui.main.MainActivity
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
            showLoading(true)
            val email = binding.tfValueEmail.text.toString().trim()
            val password = binding.tfValuePassword.text.toString().trim()

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
                    showLoading(false)
                    Intent(this, MainActivity::class.java).let {
                        startActivity(it)
                        finish()
                    }
                } else {
                    val message = task.exception.toString().split(":").toTypedArray()[1]

                    with(binding.tvAlert) {
                        text = message
                        visibility = View.VISIBLE
                    }
                }
            }
    }


    private fun showLoading(state: Boolean) {
//        binding.pbLoading.isVisible = state
    }

    companion object {
        private const val TAG = "Login Activity"
    }
}