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
    private val movieList = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModels
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        
        // Initialize RecyclerView
        movieAdapter = MovieAdapter(movieList)
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = movieAdapter
        
        // Set button click listeners
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
        
        // Load movies from Firestore
        loadMovies()
    }
    
    private fun loadMovies() {
        movieViewModel.getMoviesList { movies ->
            movieList.clear()
            movieList.addAll(movies)
            movieAdapter.notifyDataSetChanged()
            Log.i("MovieList", "Found ${movies.size} movies")
        }
    }
    
    override fun onResume() {
        super.onResume()
        loadMovies()
    }
}