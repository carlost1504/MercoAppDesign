package com.example.mercoapp.repository

import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.service.AuthService
import com.example.mercoapp.service.AuthServiceImpl
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthRepository {
    suspend fun signupBuyer(buyer: UserBuyer, password: String)
    suspend fun signupSeller(seller: UserSeller, password: String)
    suspend fun signin(email: String, password: String): FirebaseUser? // Cambia el tipo de retorno
    suspend fun signup(email: String, password: String)
}


class AuthRepositoryImpl(
    private val authService: AuthService = AuthServiceImpl(),
    private val userRepository: UserRepository = UserRepositoryImpl()
) : AuthRepository {

    override suspend fun signupBuyer(buyer: UserBuyer, password: String) {
        authService.createUser(buyer.email, password)
        val uid = Firebase.auth.currentUser?.uid
        uid?.let {
            buyer.id = it
            userRepository.createBuyer(buyer)
        }
    }

    override suspend fun signupSeller(seller: UserSeller, password: String) {
        authService.createUser(seller.email, password)
        val uid = Firebase.auth.currentUser?.uid
        uid?.let {
            seller.id = it
            userRepository.createSeller(seller)
        }
    }

    override suspend fun signup(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    // Devuelve FirebaseUser para obtener el UID
    override suspend fun signin(email: String, password: String): FirebaseUser? {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser // Devuelve el usuario autenticado
    }
}
