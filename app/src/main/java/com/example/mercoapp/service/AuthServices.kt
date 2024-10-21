package com.example.mercoapp.service

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthService {
    suspend fun createUser(email: String, password: String)
    suspend fun loginWithEmailAndPassword(email: String, password: String)
}

class AuthServiceImpl : AuthService {
    // Registrar un usuario con email y password en Firebase Authentication
    override suspend fun createUser(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    // Iniciar sesión con email y password en Firebase Authentication
    override suspend fun loginWithEmailAndPassword(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }
}
