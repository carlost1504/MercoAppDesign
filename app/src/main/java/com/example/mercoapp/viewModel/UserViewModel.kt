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

    val userState = MutableLiveData(0)


    fun resetUserState() {
        userState.value = 0
    }

    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("UserViewModel", "Iniciando carga de usuario con ID: $userId")
                withContext(Dispatchers.Main) { userState.value = 1 }  // Estado: Loading

                val fetchedUser = userRepo.getUserById(userId)
                Log.d("UserViewModel", "Datos obtenidos de UserRepo: $fetchedUser")

                withContext(Dispatchers.Main) {
                    when (fetchedUser) {
                        is UserBuyer -> {
                            _userBuyer.value = fetchedUser
                            _userSeller.value = null  // Asegura que `userSeller` esté en `null`
                            Log.d("UserViewModel", "UserBuyer cargado: ${fetchedUser.name}")
                            userState.value = 3  // Estado: Success
                        }
                        is UserSeller -> {
                            _userSeller.value = fetchedUser
                            _userBuyer.value = null  // Asegura que `userBuyer` esté en `null`
                            Log.d("UserViewModel", "UserSeller cargado: ${fetchedUser.name}")
                            userState.value = 3  // Estado: Success
                        }
                        else -> {
                            Log.d("UserViewModel", "Error: Usuario no encontrado o de tipo desconocido.")
                            userState.value = 2  // Estado: Error
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("UserViewModel", "Error al cargar usuario: ${ex.localizedMessage}")
                withContext(Dispatchers.Main) { userState.value = 2 }  // Estado: Error
            } finally {
                withContext(Dispatchers.Main) { userState.value = 0 } // Reset al estado inicial
            }
        }
    }





    // Actualizar información de UserBuyer
    fun updateUserBuyer(user: UserBuyer) {
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

    // Actualizar información de UserSeller
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

