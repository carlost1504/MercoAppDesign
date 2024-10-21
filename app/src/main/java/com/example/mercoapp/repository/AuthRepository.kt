package com.example.mercoapp.repository

import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.service.AuthService
import com.example.mercoapp.service.AuthServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface AuthRepository {
    suspend fun signupBuyer(buyer: UserBuyer, password: String)
    suspend fun signupSeller(seller: UserSeller, password: String)
    suspend fun signin(email: String, password: String)
}

class AuthRepositoryImpl(
    private val authService: AuthService = AuthServiceImpl(),
    private val userRepository: UserRepository = UserRepositoryImpl()
) : AuthRepository {

    override suspend fun signupBuyer(buyer: UserBuyer, password: String) {
        // 1. Registro en el módulo de autenticación con Firebase
        authService.createUser(buyer.email, password)

        // 2. Obtenemos el UID del usuario
        val uid = Firebase.auth.currentUser?.uid

        // 3. Guardar el comprador en Firestore
        uid?.let {
            buyer.id = it // Asignamos el UID al comprador
            userRepository.createBuyer(buyer) // Guardar el comprador en Firestore
        }
    }

    override suspend fun signupSeller(seller: UserSeller, password: String) {
        // 1. Registro en el módulo de autenticación con Firebase
        authService.createUser(seller.email, password)

        // 2. Obtenemos el UID del usuario
        val uid = Firebase.auth.currentUser?.uid

        // 3. Guardar el vendedor en Firestore
        uid?.let {
            seller.id = it // Asignamos el UID al vendedor
            userRepository.createSeller(seller) // Guardar el vendedor en Firestore
        }
    }

    override suspend fun signin(email: String, password: String) {
        // Inicio de sesión con Firebase Authentication
        authService.loginWithEmailAndPassword(email, password)
    }
}