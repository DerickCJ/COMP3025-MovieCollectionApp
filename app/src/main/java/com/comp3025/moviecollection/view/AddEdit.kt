package com.comp3025.moviecollection.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.comp3025.moviecollection.databinding.ActivityAddEditBinding
import com.comp3025.moviecollection.viewmodel.MovieViewModel

class AddEdit : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityAddEditBinding
    
    // ViewModel for movie operations
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Cancel button
        binding.btnCancel.setOnClickListener {
            finish()
        }
        
        // Save button
        binding.btnSave.setOnClickListener {
            saveMovie()
        }
    }
    
    private fun saveMovie() {
        val title = binding.etTitle.text.toString().trim()
        val year = binding.etYear.text.toString().trim()
        val poster = binding.etPoster.text.toString().trim()
        val imdbId = binding.etImdbId.text.toString().trim()
        
        // Validate input
        if (title.isEmpty()) {
            binding.etTitle.error = "Please enter movie title"
            binding.etTitle.requestFocus()
            return
        }
        
        if (year.isEmpty()) {
            binding.etYear.error = "Please enter release year"
            binding.etYear.requestFocus()
            return
        }
        
        if (poster.isEmpty()) {
            binding.etPoster.error = "Please enter poster URL"
            binding.etPoster.requestFocus()
            return
        }
        
        // Save movie to Firestore
        movieViewModel.addMovie(title, year, poster, imdbId) { success, message ->
            if (success) {
                Toast.makeText(this, "Movie saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save movie: $message", Toast.LENGTH_LONG).show()
            }
        }
    }
}