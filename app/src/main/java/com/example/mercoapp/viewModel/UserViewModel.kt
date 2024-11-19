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

    private val _userBuyer = MutableLiveData<UserBuyer?>()
    val userBuyer: LiveData<UserBuyer?> = _userBuyer

    private val _userSeller = MutableLiveData<UserSeller?>()
    val userSeller: LiveData<UserSeller?> = _userSeller

    private val _user = MediatorLiveData<Any?>().apply {
        addSource(_userBuyer) { value = it }
        addSource(_userSeller) { value = it }
    }
    val user: LiveData<Any?> = _user

    private val _userState = MutableLiveData<Int>(0) // 0: Idle
    val userState: LiveData<Int> = _userState

    fun loadCurrentUser() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId.isNullOrEmpty()) {
            Log.e("UserViewModel", "Error: userId is null or empty. User may not be authenticated.")
            updateUserState(2) // Error
            return
        }
        getUser(userId)
    }

    fun getUser(userId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            updateUserState(1) // Loading
            try {
                val fetchedUser = withContext(Dispatchers.IO) { userRepo.getUserById(userId) }
                when (fetchedUser) {
                    is UserBuyer -> handleFetchedBuyer(fetchedUser)
                    is UserSeller -> handleFetchedSeller(fetchedUser)
                    else -> {
                        Log.e("UserViewModel", "Error: Usuario no encontrado")
                        updateUserState(2) // Error
                    }
                }
            } catch (ex: Exception) {
                Log.e("UserViewModel", "Error al cargar usuario: ${ex.localizedMessage}")
                updateUserState(2) // Error
            }
        }
    }

    private fun handleFetchedBuyer(buyer: UserBuyer) {
        _userBuyer.value = buyer
        _userSeller.value = null
        updateUserState(3) // Success
    }

    private fun handleFetchedSeller(seller: UserSeller) {
        _userSeller.value = seller
        _userBuyer.value = null
        updateUserState(3) // Success
    }

    private fun updateUserState(state: Int) {
        _userState.value = state
    }
}

