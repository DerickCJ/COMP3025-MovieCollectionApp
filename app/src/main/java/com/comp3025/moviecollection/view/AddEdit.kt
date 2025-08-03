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
    
    private var isEditMode = false
    private var originalImdbID = ""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        
        // Check if this is edit mode and load data
        checkEditMode()
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe the operation completion status
        movieViewModel.operationCompleted.observe(this) { completed ->
            if (completed) {
                movieViewModel.resetOperationStatus()
                finish() // Close the activity after saving
            }
        }
    }
    private fun checkEditMode() {
        isEditMode = intent.getBooleanExtra("isEdit", false)
        
        if (isEditMode) {
            val title = intent.getStringExtra("title") ?: ""
            val year = intent.getStringExtra("year") ?: ""
            val poster = intent.getStringExtra("poster") ?: ""
            val imdbID = intent.getStringExtra("imdbID") ?: ""
            
            // Save original imdbID for update operation
            originalImdbID = imdbID
            
            // Fill the form with existing data
            binding.etTitle.setText(title)
            binding.etYear.setText(year)
            binding.etPoster.setText(poster)
            binding.etImdbId.setText(imdbID)
        }
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
        
        if (isEditMode) {
            // Update movie
            movieViewModel.updateMovie(originalImdbID, title, year, poster, imdbId)


        } else {
            // Add new movie
            movieViewModel.addMovie(title, year, poster, imdbId)
        }
    }
}