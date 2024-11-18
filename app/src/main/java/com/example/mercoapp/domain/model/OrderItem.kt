package com.example.mercoapp.domain.model

data class OrderItem(
    val name: String,
    val variety: String,
    val quantity: Int,
    val price: String,
    val imageUrl: String
)