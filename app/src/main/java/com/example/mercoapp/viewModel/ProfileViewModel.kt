package com.example.mercoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl(),
) : ViewModel() {

    private val _user = MutableLiveData<Any?>()
    val user: LiveData<Any?> get() = _user

    private var currentChatRoomID: String? = null

    // Obtener el usuario actual (puede ser Buyer o Seller)
    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = userRepository.getCurrentUser()
                withContext(Dispatchers.Main) {
                    _user.value = currentUser
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _user.value = null // Manejar el error, como mostrar un mensaje en la UI
                }
            }
        }
    }


}
