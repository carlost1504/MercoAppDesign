package com.example.mercoapp.service

import com.google.firebase.ktx.Firebase
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.tasks.await

interface UserServices {
    suspend fun createBuyer(buyer: UserBuyer)      // Crear un comprador
    suspend fun createSeller(seller: UserSeller)   // Crear un vendedor
    suspend fun getBuyerById(id: String): UserBuyer?  // Obtener comprador por ID
    suspend fun getSellerById(id: String): UserSeller?  // Obtener vendedor por ID
}

class UserServicesImpl : UserServices {

    // Crear un comprador en la colección "buyers" de Firestore
    override suspend fun createBuyer(buyer: UserBuyer) {
        Firebase.firestore
            .collection("buyers")
            .document(buyer.id)
            .set(buyer)
            .await()
    }

    // Crear un vendedor en la colección "sellers" de Firestore
    override suspend fun createSeller(seller: UserSeller) {
        Firebase.firestore
            .collection("sellers")
            .document(seller.id)
            .set(seller)
            .await()
    }

    // Obtener un comprador por su ID desde la colección "buyers"
    override suspend fun getBuyerById(id: String): UserBuyer? {
        val buyer = Firebase.firestore
            .collection("buyers")
            .document(id)
            .get()
            .await()

        // Convertir el documento Firestore en un objeto Buyer
        return buyer.toObject(UserBuyer::class.java)
    }

    // Obtener un vendedor por su ID desde la colección "sellers"
    override suspend fun getSellerById(id: String): UserSeller? {
        val seller = Firebase.firestore
            .collection("sellers")
            .document(id)
            .get()
            .await()

        // Convertir el documento Firestore en un objeto Seller
        return seller.toObject(UserSeller::class.java)
    }
}
