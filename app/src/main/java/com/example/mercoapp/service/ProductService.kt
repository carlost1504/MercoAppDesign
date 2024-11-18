package com.example.mercoapp.service

import android.net.Uri
import com.example.mercoapp.domain.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID


interface ProductService {
    suspend fun createProduct(product: Product)
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(productId: String): Product?
    suspend fun uploadProductImage(uri: Uri): String // Método para subir imágenes
}

class ProductServiceImpl : ProductService {
    private val storage = Firebase.storage

    override suspend fun createProduct(product: Product) {
        Firebase.firestore.collection("products")
            .document(product.id)
            .set(product)
            .await()
    }

    override suspend fun getProducts(): List<Product> {
        val snapshot = Firebase.firestore.collection("products").get().await()
        return snapshot.toObjects(Product::class.java)
    }

    override suspend fun getProductById(productId: String): Product? {
        val document = Firebase.firestore.collection("products")
            .document(productId)
            .get()
            .await()
        return document.toObject(Product::class.java)
    }

    override suspend fun uploadProductImage(uri: Uri): String {
        val storageRef = storage.reference.child("products/${UUID.randomUUID()}.jpg")
        val uploadTask = storageRef.putFile(uri).await() // Espera a que se complete la carga
        return storageRef.downloadUrl.await().toString() // Devuelve la URL de la imagen
    }
}