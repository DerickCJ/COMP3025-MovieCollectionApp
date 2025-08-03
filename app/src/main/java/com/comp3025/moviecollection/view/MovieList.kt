package com.comp3025.moviecollection.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp3025.moviecollection.MainActivity
import com.comp3025.moviecollection.adapter.MovieAdapter
import com.comp3025.moviecollection.databinding.ActivityMovieListBinding
import com.comp3025.moviecollection.model.Movie
import com.comp3025.moviecollection.viewmodel.AuthViewModel
import com.comp3025.moviecollection.viewmodel.MovieViewModel

class MovieList : AppCompatActivity()
{
    // View Binding
    private lateinit var binding: ActivityMovieListBinding
    // ViewModels
    private lateinit var authViewModel: AuthViewModel
    private lateinit var movieViewModel: MovieViewModel
    // RecyclerView
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModels()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        // Load movies from Database
        movieViewModel.getMoviesList()

    }


        // Initialize ViewModels
    private fun initializeViewModels()
        {
            authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
            movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        }

    // Setup RecyclerView and its adapter
    private fun setupRecyclerView(){
        // Initialize RecyclerView
        movieAdapter = MovieAdapter(
            onEditClick = { movie -> editMovie(movie) },
            onDeleteClick = { movie, position -> movieViewModel.deleteMovie(movie) }
        )

        with (binding.rvMovies) {
            layoutManager = LinearLayoutManager(this@MovieList)
            adapter = movieAdapter
        }
    }

    // Setup observers for ViewModel
    private fun setupObservers() {
        // Observe movie list changes
        movieViewModel.movieList.observe(this) { movies ->
            movieAdapter.updateMovies(movies)
        }
        // Observe authentication state
        authViewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (!isLoggedIn) {
                Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // Setup click listeners for buttons
    private fun setupClickListeners() {
        binding.fabAddMovie.setOnClickListener {
            val intent = Intent(this, AddEdit::class.java)
            startActivity(intent)
        }

        binding.fabLogout.setOnClickListener {
            authViewModel.logout()
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    // Edit movie function
    private fun editMovie(movie: Movie) {
        val intent = Intent(this, AddEdit::class.java).apply {
            putExtra("title", movie.title)
            putExtra("year", movie.year)
            putExtra("poster", movie.poster)
            putExtra("imdbID", movie.imdbID)
            putExtra("isEdit", true)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.getMoviesList() // Refresh movie list when returning to this activity
    }


   
}