package com.example.mercoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.Message
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import com.example.mercoapp.service.ChatService
import com.example.mercoapp.service.ChatServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl(),
    private val chatService: ChatService = ChatServiceImpl()
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

    // Establecer el ID de la sala de chat actual
    fun setChatRoomID(chatRoomID: String) {
        currentChatRoomID = chatRoomID
    }

    // Buscar el ID de la sala de chat entre dos usuarios
    fun searchChatRoom(buyerID: String, sellerID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val chatRoomID = chatService.searchChatId(buyerID, sellerID)
                setChatRoomID(chatRoomID)
            } catch (e: Exception) {
                // Manejar el error aquí (opcional)
            }
        }
    }

    // Enviar mensaje en la sala de chat actual
    fun sendMessage(messageContent: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currentChatRoomID?.let {
                try {
                    chatService.sendMessage(Message(UUID.randomUUID().toString(), messageContent), it)
                } catch (e: Exception) {
                    // Manejar el error aquí (opcional)
                }
            }
        }
    }

    // Obtener los mensajes de la sala de chat actual
    fun getMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            currentChatRoomID?.let {
                try {
                    chatService.getMessages(it)
                } catch (e: Exception) {
                    // Manejar el error aquí (opcional)
                }
            }
        }
    }
}
