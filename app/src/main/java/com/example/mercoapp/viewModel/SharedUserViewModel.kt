package com.example.mercoapp.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.Order
import com.example.mercoapp.domain.model.OrderStatus
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.repository.OrderRepository
import com.example.mercoapp.repository.OrderRepositoryImpl
import com.example.mercoapp.repository.ProductRepository
import com.example.mercoapp.repository.ProductRepositoryImpl
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedUserViewModel(
    private val userRepository: UserRepository = UserRepositoryImpl(),
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
    private val orderRepository: OrderRepository = OrderRepositoryImpl()
) : ViewModel() {

    // Datos del vendedor
    private val _seller = MutableLiveData<UserSeller?>()
    val seller: LiveData<UserSeller?> = _seller

    // Datos del comprador
    private val _buyer = MutableLiveData<UserBuyer?>()
    val buyer: LiveData<UserBuyer?> = _buyer

    // Productos activos globales
    private val _activeProducts = MutableLiveData<List<Product>>()
    val activeProducts: LiveData<List<Product>> = _activeProducts

    // Productos seleccionados y cantidades para la orden
    private val _selectedProducts = MutableLiveData<List<Product>>()
    val selectedProducts: LiveData<List<Product>> = _selectedProducts

    private val _productQuantities = MutableLiveData<Map<String, Int>>()
    val productQuantities: LiveData<Map<String, Int>> = _productQuantities

    // Historial de órdenes del comprador
    private val _orderHistory = MutableLiveData<List<Order>>()
    val orderHistory: LiveData<List<Order>> = _orderHistory

    // Estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Mensajes de error
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Cargar datos del vendedor por ID.
     */
    fun loadSellerData(sellerId: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sellerData = userRepository.getUserById(sellerId)
                if (sellerData is UserSeller) {
                    withContext(Dispatchers.Main) {
                        _seller.value = sellerData
                        _isLoading.value = false
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _seller.value = null
                        _isLoading.value = false
                        _error.value = "El usuario con ID $sellerId no es un vendedor."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _error.value = e.message ?: "Error desconocido al cargar datos del vendedor."
                }
            }
        }
    }

    /**
     * Cargar datos del comprador por ID.
     */
    fun loadBuyerData(buyerId: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val buyerData = userRepository.getUserById(buyerId)
                if (buyerData is UserBuyer) {
                    withContext(Dispatchers.Main) {
                        _buyer.value = buyerData
                        _isLoading.value = false
                        loadOrderHistory(buyerId) // Cargar historial de órdenes
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _buyer.value = null
                        _isLoading.value = false
                        _error.value = "El usuario con ID $buyerId no es un comprador."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _error.value = e.message ?: "Error desconocido al cargar datos del comprador."
                }
            }
        }
    }

    /**
     * Cargar todos los productos activos.
     */
    fun loadActiveProducts() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = productRepository.getProducts().filter { it.isActive }
                withContext(Dispatchers.Main) {
                    _activeProducts.value = products
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _activeProducts.value = emptyList()
                    _isLoading.value = false
                    _error.value = e.message ?: "Error desconocido al cargar productos activos."
                }
            }
        }
    }

    /**
     * Seleccionar productos y cantidades para la orden.
     */
    fun selectProducts(products: List<Product>, quantities: Map<String, Int>) {
        _selectedProducts.value = products
        _productQuantities.value = quantities
    }

    /**
     * Crear una nueva orden para el comprador actual.
     */
    fun createOrder() {
        val buyer = _buyer.value ?: return
        val items = _selectedProducts.value ?: emptyList()
        val quantities = _productQuantities.value ?: emptyMap()

        val totalPrice = items.sumOf { it.price * (quantities[it.id] ?: 1) }
        val newOrder = Order(
            buyerId = buyer.id,
            items = items,
            quantities = quantities,
            totalPrice = totalPrice
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                orderRepository.createOrder(newOrder)
                withContext(Dispatchers.Main) {
                    loadOrderHistory(buyer.id) // Actualizar historial después de crear la orden
                    clearSelectedProducts() // Limpiar selección después de crear la orden
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = e.message ?: "Error desconocido al crear la orden."
                }
            }
        }
    }

    /**
     * Limpiar la selección de productos.
     */
    private fun clearSelectedProducts() {
        _selectedProducts.value = emptyList()
        _productQuantities.value = emptyMap()
    }

    /**
     * Cargar el historial de órdenes para el comprador actual.
     */
    fun loadOrderHistory(buyerId: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val orders = orderRepository.getOrdersByBuyer(buyerId)
                withContext(Dispatchers.Main) {
                    _orderHistory.value = orders
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _orderHistory.value = emptyList()
                    _isLoading.value = false
                    _error.value = e.message ?: "Error desconocido al cargar el historial de órdenes."
                }
            }
        }
    }

    /**
     * Limpiar datos del vendedor.
     */
    fun clearSellerData() {
        _seller.value = null
        _error.value = null
        _isLoading.value = false
    }

    /**
     * Limpiar datos del comprador.
     */
    fun clearBuyerData() {
        _buyer.value = null
        _error.value = null
        _isLoading.value = false
    }


    val currentOrder: LiveData<Order> = MutableLiveData()
    fun updateOrderStatus(orderId: String, newStatus: String) {
        // Actualiza el estado del pedido en la base de datos o lógica correspondiente
        viewModelScope.launch {
            orderRepository.updateOrderStatus(orderId, OrderStatus.PENDING)
            // Opcional: Actualizar el estado local
            (currentOrder as MutableLiveData).value = orderRepository.getOrderById(orderId)
        }
    }





    fun addProductToSeller(product: Product) {
        val currentSeller = _seller.value
        if (currentSeller != null) {
            val updatedProducts = currentSeller.productIds.toMutableList()
            updatedProducts.add(product) // Agrega el nuevo producto a la lista
            currentSeller.productIds = updatedProducts // Actualiza la lista
            _seller.postValue(currentSeller) // Notifica los cambios
        }
    }

    fun removeProductFromSeller(product: Product) {
        val currentSeller = _seller.value
        if (currentSeller != null) {
            val updatedProducts = currentSeller.productIds.toMutableList()
            updatedProducts.remove(product) // Elimina el producto de la lista
            currentSeller.productIds = updatedProducts // Actualiza la lista
            _seller.postValue(currentSeller) // Notifica los cambios
        }
    }

}




