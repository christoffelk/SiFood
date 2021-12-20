package com.capstone.sifood.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.capstone.sifood.data.local.entities.Food
import com.capstone.sifood.databinding.ActivityRegisterBinding
import com.capstone.sifood.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.tfValueEmail.text.toString().trim()
            val password = binding.tfValuePassword.text.toString().trim()

            // TODO: 18/12/21 : validasi email dan password
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.tfValueEmail.error = "Email tidak Valid"
                binding.tfValueEmail.requestFocus()
                return@setOnClickListener
            }

            register(email, password)
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Intent(this, LoginActivity::class.java).let {
                        startActivity(it)
                        finish()
                    }
                } else {
                    val message = task.exception.toString().split(":").toTypedArray()[1]

                    with(binding.tvAlert){
                        text = message
                        visibility = View.VISIBLE
                    }
                }
            }
    }

    companion object{
        const val TAG = "Register"
    }
}