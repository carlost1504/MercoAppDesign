package com.example.mercoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.AuthState
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.AuthRepository
import com.example.mercoapp.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AuthViewModel(
    private val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    // Estados de autenticación
    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
    open val authState: LiveData<AuthState> = _authState

    // ID del usuario autenticado
    private val _userId = MutableLiveData<String?>()
    open val userId: LiveData<String?> = _userId

    // Función para registrar un comprador
    fun signupBuyer(buyer: UserBuyer, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { _authState.value = AuthState.Loading }

                // Registrar el comprador
                repo.signupBuyer(buyer, password)

                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Success
                }
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Correo o contraseña inválidos.")
                }
                ex.printStackTrace()
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Error al registrar el comprador.")
                }
                ex.printStackTrace()
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Error inesperado: ${ex.localizedMessage}")
                }
                ex.printStackTrace()
            }
        }
    }

    // Función para registrar un vendedor
    fun signupSeller(seller: UserSeller, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { _authState.value = AuthState.Loading }

                // Registrar el vendedor
                repo.signupSeller(seller, password)

                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Success
                }
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Correo o contraseña inválidos.")
                }
                ex.printStackTrace()
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Error al registrar el vendedor.")
                }
                ex.printStackTrace()
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Error inesperado: ${ex.localizedMessage}")
                }
                ex.printStackTrace()
            }
        }
    }

    // Función para iniciar sesión
    open fun signin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Loading
                }

                // Inicia sesión con el repositorio
                val user = repo.signin(email, password)

                if (user != null) {
                    // Usuario autenticado correctamente
                    _userId.postValue(user.uid)
                    withContext(Dispatchers.Main) {
                        _authState.value = AuthState.Success
                    }
                } else {
                    // Usuario no encontrado
                    withContext(Dispatchers.Main) {
                        _authState.value = AuthState.Error("No se pudo iniciar sesión. Intente nuevamente.")
                    }
                }
            } catch (ex: FirebaseAuthInvalidUserException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("El usuario no existe.")
                }
                ex.printStackTrace()
            } catch (ex: FirebaseAuthInvalidCredentialsException) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Correo o contraseña incorrectos.")
                }
                ex.printStackTrace()
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    _authState.value = AuthState.Error("Error inesperado: ${ex.localizedMessage}")
                }
                ex.printStackTrace()
            }
        }
    }
}

