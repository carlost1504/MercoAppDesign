package com.example.mercoapp.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// Estado de la orden
enum class OrderStatus {
    PENDING, // En espera
    PICKED_UP, // Recogida
    CANCELLED, // Cancelada
    INPROGRESS //en proceso
}

// Representación de la orden completa
data class Order(
    val id: String = UUID.randomUUID().toString(), // ID único de la orden
    val buyerId: String, // ID del comprador asociado
    val items: List<Product>, // Lista de productos en la orden
    val quantities: Map<String, Int>, // Mapa de cantidades, con el ID del producto como clave
    val totalPrice: Double, // Precio total
    val status: OrderStatus = OrderStatus.PENDING, // Estado inicial
    val orderDate: String = getCurrentDate(), // Fecha de creación
    val pickupDate: String? = null // Fecha de recogida (opcional)
)

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Puedes cambiar el formato según necesites
    return dateFormat.format(Date())
}