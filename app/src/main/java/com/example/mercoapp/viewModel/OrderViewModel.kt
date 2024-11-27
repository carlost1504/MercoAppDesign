package com.example.mercoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.Order
import com.example.mercoapp.domain.model.OrderStatus
import com.example.mercoapp.repository.OrderRepository
import com.example.mercoapp.repository.OrderRepositoryImpl
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository = OrderRepositoryImpl()
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    private val _orderDetails = MutableLiveData<Order?>()
    val orderDetails: LiveData<Order?> get() = _orderDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun createOrder(order: Order) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                orderRepository.createOrder(order)
                loadOrdersByBuyer(order.buyerId) // Recargar las órdenes del comprador
            } catch (e: Exception) {
                _error.value = "Error al crear la orden: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadOrdersByBuyer(buyerId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _orders.value = orderRepository.getOrdersByBuyer(buyerId)
            } catch (e: Exception) {
                _error.value = "Error al cargar órdenes: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadOrderDetails(orderId: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _orderDetails.value = orderRepository.getOrderById(orderId)
            } catch (e: Exception) {
                _error.value = "Error al cargar detalles de la orden: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        viewModelScope.launch {
            try {
                orderRepository.updateOrderStatus(orderId, status)
                _orders.value = _orders.value?.map {
                    if (it.id == orderId) it.copy(status = status) else it
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar el estado: ${e.localizedMessage}"
            }
        }
    }
}
