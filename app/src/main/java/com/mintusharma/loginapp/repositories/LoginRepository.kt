package com.mintusharma.loginapp.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val auth: FirebaseAuth) {

    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).isSuccessful
                result
            } catch (e: Exception) {
                e.message?.let { Log.d("signInWithEmail:error", it) }
                false
            }
        }

    }
}