package com.example.mercoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.AuthRepository
import com.example.mercoapp.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AuthViewModel(
    private val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    // Estados de autenticación
    open val authState = MutableLiveData(0)
    // 0. Idle - No hay operación en curso
    // 1. Loading - Operación en proceso
    // 2. Error - Ocurrió un error
    // 3. Success - Operación exitosa

    // ID del usuario autenticado
    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> = _userId

    // Función para registrar un comprador
    fun signupBuyer(buyer: UserBuyer, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 } // Estado: Loading
            try {
                repo.signupBuyer(buyer, password) // Registrar el comprador
                withContext(Dispatchers.Main) { authState.value = 3 } // Estado: Success
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 } // Estado: Error
                ex.printStackTrace()
            }
        }
    }

    // Función para registrar un vendedor
    fun signupSeller(seller: UserSeller, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 } // Estado: Loading
            try {
                repo.signupSeller(seller, password) // Registrar el vendedor
                withContext(Dispatchers.Main) { authState.value = 3 } // Estado: Success
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 } // Estado: Error
                ex.printStackTrace()
            }
        }
    }

    // Función para iniciar sesión
    fun signin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                if (authState.value != 1) { // Solo cambia a 'Loading' si no está en ese estado
                    authState.value = 1
                }
            }
            try {
                val user = repo.signin(email, password) // Intento de log in
                if (user != null) {  // Asegurarse de que user es válido antes de asignar el UID
                    _userId.postValue(user.uid)
                    withContext(Dispatchers.Main) { authState.value = 3 } // Estado: Success
                } else {
                    withContext(Dispatchers.Main) { authState.value = 2 } // Estado: Error
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 } // Estado: Error
                ex.printStackTrace()
            }
        }
    }
}

