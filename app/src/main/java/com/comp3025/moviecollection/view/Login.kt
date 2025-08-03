package com.comp3025.moviecollection.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.comp3025.moviecollection.databinding.ActivityLoginBinding
import com.comp3025.moviecollection.viewmodel.AuthViewModel

class Login : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityLoginBinding
    
    // ViewModel for authentication
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe login state from ViewModel
        authViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                // If user is already logged in, navigate to MovieList
                val intent = Intent(this, MovieList::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    
    private fun setupClickListeners() {
        // Login button click event
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
        
        // Cancel button click event
        binding.btnCancelLogin.setOnClickListener {
            finish()
        }
        
        // Register link click event
        binding.tvGoRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
    
    private fun loginUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        
        if (validateInput(email, password)) {
            authViewModel.loginUser(email, password)
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        // Check if email is empty
        if (email.isEmpty()) {
            binding.etEmail.error = "Please enter email address"
            binding.etEmail.requestFocus()
            return false
        }
        
        // Check if password is empty
        if (password.isEmpty()) {
            binding.etPassword.error = "Please enter password"
            binding.etPassword.requestFocus()
            return false
        }
        
        return true
    }

}