package com.capstone.hibykes.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.capstone.hibykes.R
import com.capstone.hibykes.databinding.ActivityRegisterBinding
import com.capstone.hibykes.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> registerUser()
            R.id.btn_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        binding.apply {

            if (email.isEmpty()) {
                etEmail.error = "Please enter Username!"
                etEmail.requestFocus()
                return
            }
            if (password.isEmpty()) {
                etPassword.error = "Please enter Password!"
                etPassword.requestFocus()
                return
            }
            if (password != confirmPassword) {
                etConfirmPassword.error = "Password is not same!"
                etConfirmPassword.requestFocus()
                return
            }
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                Log.d("Register", "createUserWithEmail:success")
                val user = auth.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener{
                    if (it.isSuccessful) {
                        Toast.makeText(baseContext, "Email verification send!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                Log.w("Register", "createUserWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}