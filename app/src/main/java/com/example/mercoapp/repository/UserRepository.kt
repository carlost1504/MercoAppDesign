package com.example.mercoapp.repository

import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.service.UserServices
import com.example.mercoapp.service.UserServicesImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface UserRepository {
    suspend fun createBuyer(buyer: UserBuyer)      // Crear un comprador
    suspend fun createSeller(seller: UserSeller)   // Crear un vendedor
    suspend fun getCurrentUser(): Any?         // Obtener el usuario actual (podría ser Buyer o Seller)
}

class UserRepositoryImpl(
    private val userServices: UserServices = UserServicesImpl()
) : UserRepository {

    // Crear un comprador y almacenarlo en Firestore
    override suspend fun createBuyer(buyer: UserBuyer) {
        userServices.createBuyer(buyer)
    }

    // Crear un vendedor y almacenarlo en Firestore
    override suspend fun createSeller(seller: UserSeller) {
        userServices.createSeller(seller)
    }

    // Obtener el usuario actual de Firebase Authentication
    override suspend fun getCurrentUser(): Any? {
        val uid = Firebase.auth.currentUser?.uid

        // Verificamos si el usuario está autenticado
        return uid?.let {
            // Primero buscamos en la colección de compradores
            val buyer = userServices.getBuyerById(it)
            if (buyer != null) {
                return buyer // Si encontramos un comprador, lo retornamos
            }

            // Si no es comprador, buscamos en la colección de vendedores
            val seller = userServices.getSellerById(it)
            return seller ?: run {
                null // Si no encontramos ni comprador ni vendedor, retornamos null
            }
        }
    }
}

