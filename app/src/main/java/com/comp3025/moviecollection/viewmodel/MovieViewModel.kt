package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.comp3025.moviecollection.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.comp3025.moviecollection.api.MovieApi

class MovieViewModel : ViewModel() {
    // LiveData to hold the list of movies
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    // Get the movie list from the API
    fun getMoviesList()
    {
        MovieApi.getMoviesList { movies ->
            _movieList.value = movies
        }
    }

    // Add a new movie to the collection
    fun addMovie(title: String, year: String, poster: String, imdbId: String)
    {
        val movie = Movie(title = title, year = year, poster = poster, imdbID = imdbId)
        MovieApi.addMovie(movie) { success, message ->
            if (success) {
                getMoviesList()
            }
        }
    }

    // Update an existing movie in the collection
    fun updateMovie(oldImdbID: String, title: String, year: String, poster: String, imdbId: String)
    {
        val updatedMovie = mapOf(
            "title" to title,
            "year" to year,
            "poster" to poster,
            "imdbID" to imdbId
        )
        MovieApi.updateMovie(oldImdbID, updatedMovie) { success, message ->
            if (success) {
                getMoviesList()
            }
        }
    }

    // Delete a movie from the collection
    fun deleteMovie(movie: Movie)
    {
        MovieApi.deleteMovie(movie.imdbID) { success, message ->
            if (success) {
                getMoviesList()
            }
        }
    }
}
