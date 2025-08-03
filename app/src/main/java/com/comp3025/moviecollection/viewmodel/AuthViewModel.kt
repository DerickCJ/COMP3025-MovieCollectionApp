package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.comp3025.moviecollection.model.AppUser
import com.comp3025.moviecollection.api.UserApi
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    // LiveData to observe login state
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    init
    {
        // Initialize login state based on current user
        _isLoggedIn.value = FirebaseAuth.getInstance().currentUser != null
    }

    fun registerUser(user: AppUser, password: String)
    {
        UserApi.registerUser(user, password) { success, message ->
            if (success) {
                _isLoggedIn.value = true
            }
        }
    }

    fun loginUser(email: String, password: String)
    {
        UserApi.loginUser(email, password) { success, message ->
            if (success) {
                _isLoggedIn.value = true
            } else {
                _isLoggedIn.value = false
            }
        }
    }

    fun logout() {
        UserApi.logout()
        _isLoggedIn.value = false
    }
}