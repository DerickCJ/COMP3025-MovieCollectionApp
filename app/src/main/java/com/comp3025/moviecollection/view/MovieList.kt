package com.comp3025.moviecollection.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.comp3025.moviecollection.MainActivity
import com.comp3025.moviecollection.databinding.ActivityMovieListBinding
import com.comp3025.moviecollection.viewmodel.AuthViewModel

class MovieList : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityMovieListBinding
    
    // ViewModel for authentication
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Logout button click event
        binding.fabLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun logout() {
        authViewModel.logout()
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        
        // Go back to main page
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}