package com.example.mercoapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.AuthRepository
import com.example.mercoapp.repository.AuthRepositoryImpl
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


open class SignupViewModel(
    val authRepo: AuthRepository = AuthRepositoryImpl(),  // Repositorio de autenticación
    val userRepo: UserRepository = UserRepositoryImpl()   // Repositorio para guardar usuarios en Firestore
) : ViewModel() {

    val authState = MutableLiveData(0)
    //0. Idle
    //1. Loading
    //2. Error
    //3. Success

    // Registro de un comprador (UserBuyer)
    fun signupBuyer(buyer: UserBuyer, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 }  // Estado: Loading
            try {
                // Primero, registramos al usuario en Firebase Authentication
                authRepo.signup(buyer.email, password)

                // Luego, obtenemos el UID del usuario registrado
                val uid = Firebase.auth.currentUser?.uid
                if (uid != null) {
                    // Asignamos el UID al comprador y lo guardamos en Firestore
                    val buyerWithUid = buyer.copy(id = uid)
                    userRepo.createBuyer(buyerWithUid)
                    withContext(Dispatchers.Main) { authState.value = 3 }  // Estado: Success
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 }  // Estado: Error
                ex.printStackTrace()
            }
        }
    }

    // Registro de un vendedor (UserSeller)
    fun signupSeller(seller: UserSeller, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { authState.value = 1 }  // Estado: Loading
            try {
                // Primero, registramos al usuario en Firebase Authentication
                authRepo.signup(seller.email, password)

                // Luego, obtenemos el UID del usuario registrado
                val uid = Firebase.auth.currentUser?.uid
                if (uid != null) {
                    // Asignamos el UID al vendedor y lo guardamos en Firestore
                    val sellerWithUid = seller.copy(id = uid)
                    userRepo.createSeller(sellerWithUid)
                    withContext(Dispatchers.Main) { authState.value = 3 }  // Estado: Success
                }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { authState.value = 2 }  // Estado: Error
                ex.printStackTrace()
            }
        }
    }


}
