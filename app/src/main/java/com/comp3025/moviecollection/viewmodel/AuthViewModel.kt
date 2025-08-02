package com.comp3025.moviecollection.viewmodel

import androidx.lifecycle.ViewModel
import com.comp3025.moviecollection.model.AppUser
import com.comp3025.moviecollection.api.UserApi

class AuthViewModel : ViewModel() {

    fun registerUser(user: AppUser, password: String, callback: (Boolean, String) -> Unit) {
        UserApi.registerUser(user, password, callback)
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        UserApi.loginUser(email, password, callback)
    }

    fun logout() {
        UserApi.logout()
    }
}