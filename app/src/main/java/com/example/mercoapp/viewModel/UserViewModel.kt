package com.example.mercoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class UserViewModel(
    private val userRepo: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    // LiveData privadas para compradores y vendedores
    val _userBuyer = MutableLiveData<UserBuyer?>()
    val userBuyer: LiveData<UserBuyer?> = _userBuyer

    private val _userSeller = MutableLiveData<UserSeller?>()
    val userSeller: LiveData<UserSeller?> = _userSeller

    // LiveData combinada para exponer el usuario actual (comprador o vendedor)
    private val _user = MediatorLiveData<Any?>().apply {
        addSource(_userBuyer) { value = it }
        addSource(_userSeller) { value = it }
    }
    val user: LiveData<Any?> = _user

    // Estado del usuario
    val _userState = MutableLiveData<Int>()
    val userState: LiveData<Int> = _userState

    // Método para cargar el usuario actual
    fun loadCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId.isNullOrEmpty()) {
                Log.e("UserViewModel", "Error: userId is null or empty. User may not be authenticated.")
                updateUserState(2) // Estado: Error
                return@launch
            }

            // Carga el usuario usando su ID
            getUser(userId)
        }
    }


    // Obtener un usuario específico por ID
    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUserState(1) // Estado: Cargando

                // Llama al repositorio para obtener el usuario
                val fetchedUser = userRepo.getUserById(userId)

                // Maneja el usuario dependiendo de su tipo
                withContext(Dispatchers.Main) {
                    when (fetchedUser) {
                        is UserBuyer -> handleFetchedBuyer(fetchedUser)
                        is UserSeller -> handleFetchedSeller(fetchedUser)
                        else -> {
                            Log.e("UserViewModel", "Error: Usuario no encontrado")
                            updateUserState(2) // Estado: Error
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("UserViewModel", "Error al cargar usuario: ${ex.localizedMessage}")
                updateUserState(2) // Estado: Error
            }
        }
    }

    // Manejar usuario comprador
    private fun handleFetchedBuyer(buyer: UserBuyer) {
        _userBuyer.postValue(buyer)
        _userSeller.postValue(null)
        updateUserState(3) // Estado: Éxito
    }

    // Manejar usuario vendedor
    private fun handleFetchedSeller(seller: UserSeller) {
        _userSeller.postValue(seller)
        _userBuyer.postValue(null)
        updateUserState(3) // Estado: Éxito
    }

    // Actualizar el estado del usuario
    private fun updateUserState(state: Int) {
        if (_userState.value != state) {
            _userState.postValue(state)
        }
    }
}





