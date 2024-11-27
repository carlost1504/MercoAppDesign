package com.example.mercoapp.service

import com.example.mercoapp.domain.model.Order
import com.example.mercoapp.domain.model.OrderStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface OrderService {
    suspend fun createOrder(order: Order): String // Crea una orden y devuelve su ID
    suspend fun getOrderById(orderId: String): Order? // Obtiene una orden por su ID
    suspend fun getOrdersByBuyer(buyerId: String): List<Order> // Obtiene todas las órdenes de un comprador
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) // Actualiza el estado de una orden
    suspend fun deleteOrder(orderId: String) // Elimina una orden por su ID
}
class OrderServiceImpl : OrderService {

    private val firestore = Firebase.firestore

    override suspend fun createOrder(order: Order): String {
        val orderRef = firestore.collection("orders").document(order.id)
        orderRef.set(order).await() // Guarda la orden en Firestore
        return order.id
    }

    override suspend fun getOrderById(orderId: String): Order? {
        val orderSnapshot = firestore.collection("orders").document(orderId).get().await()
        return orderSnapshot.toObject(Order::class.java)
    }

    override suspend fun getOrdersByBuyer(buyerId: String): List<Order> {
        val querySnapshot = firestore.collection("orders")
            .whereEqualTo("buyerId", buyerId)
            .get()
            .await()
        return querySnapshot.toObjects(Order::class.java)
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        val orderRef = firestore.collection("orders").document(orderId)
        orderRef.update("status", status.name).await() // Actualiza solo el campo `status`
    }

    override suspend fun deleteOrder(orderId: String) {
        firestore.collection("orders").document(orderId).delete().await()
    }
}
