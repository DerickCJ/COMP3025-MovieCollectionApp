package com.comp3025.moviecollection.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.comp3025.moviecollection.model.AppUser

object UserApi
{
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(user: AppUser, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    val fullUser = user.copy(uid = uid)

                    // 写入完整用户信息到 Firestore
                    firestore.collection("users")
                        .document(uid)
                        .set(fullUser)
                        .addOnSuccessListener {
                            callback(true, "Registration successful!")
                        }
                        .addOnFailureListener {
                            callback(false, "User saved, but failed to store extra info.")
                        }

                } else {
                    val error = task.exception?.message ?: "Registration failed"
                    callback(false, error)
                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Login successful!")
                } else {
                    val error = task.exception?.message ?: "Login failed"
                    callback(false, error)
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

}