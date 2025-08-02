package com.comp3025.moviecollection.api

import com.comp3025.moviecollection.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object MovieApi {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun addMovie(movie: Movie, callback: (Boolean, String) -> Unit) {
        val currentUser = auth.currentUser ?: run {
            callback(false, "User not logged in")
            return
        }

        firestore.collection("users")
            .document(currentUser.uid)
            .collection("movies")
            .add(movie)
            .addOnSuccessListener {
                callback(true, "Movie saved successfully!")
            }
            .addOnFailureListener { e ->
                callback(false, "Failed to save movie: ${e.message}")
            }
    }

    fun getMoviesList(callback: (List<Movie>) -> Unit) {
        val currentUser = auth.currentUser ?: run {
            callback(emptyList())
            return
        }

        firestore.collection("users")
            .document(currentUser.uid)
            .collection("movies")
            .get()
            .addOnSuccessListener { documents ->
                val movies = documents.toObjects(Movie::class.java)
                callback(movies)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun updateMovie(oldImdbID: String, updatedMovie: Map<String, Any>, callback: (Boolean, String) -> Unit) {
        val currentUser = auth.currentUser ?: run {
            callback(false, "User not logged in")
            return
        }

        firestore.collection("users")
            .document(currentUser.uid)
            .collection("movies")
            .whereEqualTo("imdbID", oldImdbID)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty()) {
                    callback(false, "Movie not found")
                    return@addOnSuccessListener
                }

                documents.documents[0].reference.update(updatedMovie)
                    .addOnSuccessListener {
                        callback(true, "Movie updated successfully!")
                    }
                    .addOnFailureListener { e ->
                        callback(false, "Failed to update: ${e.message}")
                    }
            }
            .addOnFailureListener {
                callback(false, "Failed to find movie: ${it.message}")
            }
    }

    fun deleteMovie(imdbID: String, callback: (Boolean, String) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return callback(false, "User not logged in")

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .collection("movies")
            .whereEqualTo("imdbID", imdbID)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback(false, "Movie not found")
                    return@addOnSuccessListener
                }

                documents.documents[0].reference
                    .delete()
                    .addOnSuccessListener {
                        callback(true, "Movie deleted successfully")
                    }
                    .addOnFailureListener { e ->
                        callback(false, "Delete failed: ${e.message}")
                    }
            }
            .addOnFailureListener {
                callback(false, "Query failed")
            }
    }
}