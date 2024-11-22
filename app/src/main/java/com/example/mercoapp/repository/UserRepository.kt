package com.example.mercoapp.repository

import android.util.Log
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.service.UserServices
import com.example.mercoapp.service.UserServicesImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface UserRepository {
    suspend fun createBuyer(buyer: UserBuyer) // Crear un comprador
    suspend fun createSeller(seller: UserSeller) // Crear un vendedor
    suspend fun getCurrentUser(): Any? // Obtener el usuario actual (Buyer o Seller)
    suspend fun getUserById(userId: String): Any? // Obtener usuario específico por ID
    suspend fun updateUser(user: Any) // Actualizar usuario (Buyer o Seller)

    suspend fun getProductsBySeller(sellerId: String): List<String> // Obtener productos asociados al vendedor
    suspend fun addProductToSeller(sellerId: String, product: Product)// Asociar producto al vendedor
}

class UserRepositoryImpl(
    private val userServices: UserServices = UserServicesImpl()
) : UserRepository {

    /**
     * Obtener lista de productos asociados al vendedor por su ID.
     */
    override suspend fun getProductsBySeller(sellerId: String): List<String> {
        val seller = userServices.getSellerById(sellerId)
        // Asegúrate de convertir los IDs a Strings si vienen como Any
        return seller?.productIds?.mapNotNull { it.toString() } ?: emptyList()
    }

    /**
     * Crear un comprador y almacenarlo en Firestore.
     */
    override suspend fun createBuyer(buyer: UserBuyer) {
        userServices.createBuyer(buyer)
    }

    /**
     * Crear un vendedor y almacenarlo en Firestore.
     */
    override suspend fun createSeller(seller: UserSeller) {
        userServices.createSeller(seller)
    }

    /**
     * Obtener el usuario actual autenticado.
     */
    override suspend fun getCurrentUser(): Any? {
        val uid = Firebase.auth.currentUser?.uid

        return uid?.let {
            // Buscar en la colección de compradores
            val buyer = userServices.getBuyerById(it)
            if (buyer != null) {
                return buyer // Si es un comprador, lo retorna
            }

            // Si no es comprador, buscar en la colección de vendedores
            val seller = userServices.getSellerById(it)
            return seller ?: run {
                Log.e("UserRepositoryImpl", "Usuario actual no encontrado en compradores ni vendedores")
                null // Si no se encuentra, retorna null
            }
        }
    }

    /**
     * Obtener un usuario específico por su ID.
     */
    override suspend fun getUserById(userId: String): Any? {
        // Buscar en la colección de compradores
        val buyer = userServices.getBuyerById(userId)
        if (buyer != null) return buyer

        // Buscar en la colección de vendedores
        val seller = userServices.getSellerById(userId)
        return seller
    }

    /**
     * Actualizar información de un usuario (comprador o vendedor).
     */
    override suspend fun updateUser(user: Any) {
        when (user) {
            is UserBuyer -> userServices.updateBuyer(user) // Actualizar comprador
            is UserSeller -> userServices.updateSeller(user) // Actualizar vendedor
            else -> throw IllegalArgumentException("Tipo de usuario no soportado: ${user::class.java}")
        }
    }

    /**
     * Agregar un producto al vendedor y actualizar Firestore.
     */
    override suspend fun addProductToSeller(sellerId: String, product: Product) {
        val seller = userServices.getSellerById(sellerId) // Obtén el vendedor actual
        seller?.let {
            val updatedProductList = it.productIds.toMutableList()
            updatedProductList.add(product) // Agrega el producto a la lista
            it.productIds = updatedProductList // Actualiza la lista de productos
            userServices.updateSeller(it) // Actualiza el vendedor completo en Firestore
        } ?: throw Exception("Seller not found")
    }
}


