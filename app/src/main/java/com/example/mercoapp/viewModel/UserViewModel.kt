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
import com.google.firebase.auth.FirebaseAuth
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

    fun loadCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId == null) {
                Log.e("UserViewModel", "Error: userId is null. User may not be authenticated.")
                withContext(Dispatchers.Main) { userState.value = 2 }
                return@launch
            }

            getUser(userId)
        }
    }

    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { userState.value = 1 }  // Estado de carga

                // Intentamos obtener el usuario de Firebase
                val fetchedUser = userRepo.getUserById(userId)

                withContext(Dispatchers.Main) {
                    when (fetchedUser) {
                        is UserBuyer -> {
                            _userBuyer.value = fetchedUser
                            _userSeller.value = null
                            userState.value = 3  // Estado: éxito
                        }
                        is UserSeller -> {
                            _userSeller.value = fetchedUser
                            _userBuyer.value = null
                            userState.value = 3  // Estado: éxito
                        }
                        else -> {
                            userState.value = 2  // Estado: error si el usuario no es encontrado
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("UserViewModel", "Error al cargar usuario: ${ex.localizedMessage}")
                withContext(Dispatchers.Main) { userState.value = 2 }
            }
        }
    }
}


