package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.ViewModel
import com.comp3025.moviecollection.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.comp3025.moviecollection.api.MovieApi

class MovieViewModel : ViewModel() {

    fun addMovie(title: String, year: String, poster: String, imdbId: String, callback: (Boolean, String) -> Unit) {
        val movie = Movie(title = title, year = year, poster = poster, imdbID = imdbId)
        MovieApi.addMovie(movie, callback)
    }

    fun getMoviesList(callback: (List<Movie>) -> Unit) {
        MovieApi.getMoviesList(callback)
    }

    fun updateMovie(oldImdbID: String, title: String, year: String, poster: String, imdbId: String, callback: (Boolean, String) -> Unit) {
        val updatedMovie = mapOf(
            "title" to title,
            "year" to year,
            "poster" to poster,
            "imdbID" to imdbId
        )
        MovieApi.updateMovie(oldImdbID, updatedMovie, callback)
    }

    fun deleteMovie(imdbID: String, callback: (Boolean, String) -> Unit) {
        MovieApi.deleteMovie(imdbID, callback)
    }
}
