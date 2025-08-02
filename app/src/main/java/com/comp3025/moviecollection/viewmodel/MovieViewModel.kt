package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.ViewModel
import com.comp3025.moviecollection.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MovieViewModel : ViewModel() {
    
    // Firebase instances
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    fun addMovie(title: String, year: String, poster: String, imdbId: String, callback: (Boolean, String) -> Unit) {
        // Get current user
        val currentUser = auth.currentUser
        if (currentUser == null) {
            callback(false, "User not logged in")
            return
        }
        
        // Create movie object
        val movie = Movie(
            title = title,
            year = year,
            poster = poster,
            imdbID = imdbId
        )
        
        // Save to Firestore
        firestore.collection("users")
            .document(currentUser.uid)
            .collection("movies")
            .add(movie)
            .addOnSuccessListener {
                callback(true, "Movie saved successfully!")
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to save movie: ${exception.message}")
            }
    }
}