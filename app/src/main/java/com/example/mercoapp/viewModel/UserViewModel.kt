package com.example.mercoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(
    private val userRepo: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _userBuyer = MutableLiveData<UserBuyer?>()
    val userBuyer: LiveData<UserBuyer?> = _userBuyer

    private val _userSeller = MutableLiveData<UserSeller?>()
    val userSeller: LiveData<UserSeller?> = _userSeller

    val userState = MutableLiveData(0)  // Estado del usuario (0: idle, 1: loading, 2: error, 3: success)

    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Cambia el estado a "loading"
                withContext(Dispatchers.Main) { userState.value = 1 }

                // Intentamos obtener el usuario de Firebase
                val fetchedUser = userRepo.getUserById(userId)

                withContext(Dispatchers.Main) {
                    // Si es un `UserBuyer`, actualizamos los datos del comprador
                    when (fetchedUser) {
                        is UserBuyer -> {
                            _userBuyer.value = fetchedUser
                            _userSeller.value = null
                            userState.value = 3  // Estado: Success
                        }
                        is UserSeller -> {
                            _userSeller.value = fetchedUser
                            _userBuyer.value = null
                            userState.value = 3  // Estado: Success
                        }
                        else -> {
                            userState.value = 2  // Estado: Error si el usuario no es encontrado
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("UserViewModel", "Error al cargar usuario: ${ex.localizedMessage}")
                withContext(Dispatchers.Main) { userState.value = 2 }
            }
        }
    }

    // Método para actualizar la información de un comprador
    fun updateUserBuyer(user: UserBuyer) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { userState.value = 1 }  // Estado: Loading
                userRepo.updateUser(user)
                withContext(Dispatchers.Main) { userState.value = 3 }  // Estado: Success
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) { userState.value = 2 }  // Estado: Error
                ex.printStackTrace()
            }
        }
    }

    // Método para actualizar la información de un vendedor
    fun updateUserSeller(user: UserSeller) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { userState.value = 1 }
                userRepo.updateUser(user)
                withContext(Dispatchers.Main) { userState.value = 3 }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) { userState.value = 2 }
                ex.printStackTrace()
            }
        }
    }
}


