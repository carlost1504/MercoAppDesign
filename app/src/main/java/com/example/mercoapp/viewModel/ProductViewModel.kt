package com.example.mercoapp.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.repository.ProductRepository
import com.example.mercoapp.repository.ProductRepositoryImpl
import com.example.mercoapp.repository.UserRepository
import com.example.mercoapp.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class ProductViewModel(
    private val productRepo: ProductRepository = ProductRepositoryImpl(),
    private val userRepo: UserRepository = UserRepositoryImpl()
) : ViewModel() {

    // LiveData para manejar el estado de carga
    open val isLoading = MutableLiveData<Boolean>()

    // LiveData para almacenar el resultado de subir una imagen
    val uploadImageResult = MutableLiveData<Result<String>>()

    // LiveData para los productos del vendedor
    open val sellerProducts = MutableLiveData<List<Product>>()

    // LiveData para manejar mensajes de error
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    /**
     * Subir imagen del producto.
     */
    fun uploadProductImage(uri: Uri, onComplete: (Result<String>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                val imageUrl = productRepo.uploadProductImage(uri)
                uploadImageResult.postValue(Result.success(imageUrl))
                onComplete(Result.success(imageUrl))
            } catch (e: Exception) {
                uploadImageResult.postValue(Result.failure(e))
                onComplete(Result.failure(e))
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun addProductToSeller(
        sellerId: String,
        product: Product,
        onComplete: (Result<Unit>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Directamente agrega el producto al vendedor
                userRepo.addProductToSeller(sellerId, product)

                // Si todo salió bien, notifica éxito
                withContext(Dispatchers.Main) {
                    onComplete(Result.success(Unit))
                }
            } catch (e: Exception) {
                // Manejo de errores
                withContext(Dispatchers.Main) {
                    onComplete(Result.failure(e))
                }
            }
        }
    }

    /**
     * Obtener productos asociados a un vendedor.
     */
    fun getProductsBySeller(sellerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                val productIds = userRepo.getProductsBySeller(sellerId) // Obtén IDs de productos del vendedor
                val products = productIds.mapNotNull { productId ->
                    productRepo.getProductById(productId) // Obtén cada producto por su ID
                }
                sellerProducts.postValue(products)
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al obtener productos del vendedor: ${e.localizedMessage}")
                sellerProducts.postValue(emptyList())
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    /**
     * Manejo de errores de validación.
     */
    fun onValidationError(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _errorState.value = message // Actualiza el estado del error
            Log.e("ProductViewModel", "Error de validación: $message") // Log para depuración
        }
    }

    /**
     * Manejo de errores al subir imágenes.
     */
    fun onImageUploadError(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _errorState.value = "Error al subir la imagen: $message" // Actualiza el estado del error
            Log.e("ProductViewModel", "Error al subir la imagen: $message") // Log para depuración
        }
    }

    /**
     * Limpiar estado de error después de mostrarlo.
     */
    fun clearError() {
        viewModelScope.launch(Dispatchers.Main) {
            _errorState.value = null // Limpia el estado del error
        }
    }
}


