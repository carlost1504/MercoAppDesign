package com.example.mercoapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double, // Asegúrate de que sea Double si `priceValue` es Double
    val variety: String,
    val isActive: Boolean
)
