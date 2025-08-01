package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    
    private val auth = FirebaseAuth.getInstance()
    
    fun registerUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Registration successful!")
                } else {
                    val errorMessage = task.exception?.message ?: "Registration failed"
                    callback(false, errorMessage)
                }
            }
    }
}