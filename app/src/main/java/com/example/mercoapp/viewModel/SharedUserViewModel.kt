package com.example.mercoapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedUserViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    private val _seller = MutableLiveData<UserSeller?>()
    val seller: LiveData<UserSeller?> = _seller

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadSellerData(sellerId: String) {
        _isLoading.value = true // Inicia el estado de carga
        _error.value = null     // Reinicia el error
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sellerData = userRepository.getUserById(sellerId)
                if (sellerData is UserSeller) {
                    withContext(Dispatchers.Main) {
                        _seller.value = sellerData
                        _isLoading.value = false // Finaliza la carga
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _seller.value = null
                        _isLoading.value = false // Finaliza la carga
                        _error.value = "El usuario con ID $sellerId no es un vendedor."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false // Finaliza la carga
                    _error.value = e.message ?: "Error desconocido al cargar datos del vendedor."
                }
            }
        }
    }

    fun clearSellerData() {
        _seller.value = null
        _error.value = null
        _isLoading.value = false
    }
}
