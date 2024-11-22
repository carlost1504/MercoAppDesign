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

    private val _seller = MutableLiveData<UserSeller>()
    val seller: LiveData<UserSeller> = _seller

    fun loadSellerData(sellerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sellerData = userRepository.getUserById(sellerId)
            if (sellerData is UserSeller) { // Verifica que el resultado sea un UserSeller
                withContext(Dispatchers.Main) {
                    _seller.value = sellerData
                }
            } else {
                // Maneja el caso en que el usuario no sea un vendedor
                Log.e("SharedUserViewModel", "El usuario con ID $sellerId no es un vendedor.")
            }
        }
    }
}
