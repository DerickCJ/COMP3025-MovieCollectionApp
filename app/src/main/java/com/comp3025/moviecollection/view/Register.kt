package com.comp3025.moviecollection.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.comp3025.moviecollection.databinding.ActivityRegisterBinding
import com.comp3025.moviecollection.viewmodel.AuthViewModel

class Register: AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityRegisterBinding
    
    // ViewModel for authentication
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        
        // Setup button click listeners
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Register button click event
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        
        // Cancel, return to main page
        binding.btnCancelRegister.setOnClickListener {
            finish()
        }
    }
    
    private fun registerUser() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        
        if (!validateInput(email, password, confirmPassword)) {
            return
        }
        
        // Call ViewModel to register user
        authViewModel.registerUser(email, password) { success, message ->
            if (success) {
                Toast.makeText(this, "Registration successful! Please login", Toast.LENGTH_SHORT).show()
                // Navigate to login page
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
                
            } else {
                Toast.makeText(this, "Registration failed: $message", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
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
        
        // Check password length
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            binding.etPassword.requestFocus()
            return false
        }
        
        // Check if confirm password is empty
        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Please confirm password"
            binding.etConfirmPassword.requestFocus()
            return false
        }
        
        // Check if passwords match
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "The two passwords do not match"
            binding.etConfirmPassword.requestFocus()
            return false
        }
        
        return true
    }

}