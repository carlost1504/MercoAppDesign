package com.example.mercoapp.domain.model

sealed class AuthState {
    object Idle : AuthState() // Estado inicial
    object Loading : AuthState() // Cargando
    object Success : AuthState() // Éxito
    data class Error(val message: String) : AuthState() // Error con mensaje
}
