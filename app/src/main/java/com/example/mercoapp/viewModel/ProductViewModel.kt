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

open class ProductViewModel(
    private val productRepo: ProductRepository = ProductRepositoryImpl(),
    private val userRepo: UserRepository = UserRepositoryImpl()
) : ViewModel() {
    open val isLoading = MutableLiveData<Boolean>()
    val uploadImageResult = MutableLiveData<Result<String>>()
    open val sellerProducts = MutableLiveData<List<Product>>()

    // Subir imagen
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

    // Crear producto y vincular al vendedor
    fun addProductToSeller(sellerId: String, product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productRepo.createProduct(product) // Guarda el producto en Firestore
                userRepo.addProductToSeller(sellerId, product.id) // Vincula el producto al vendedor
                Log.d("ProductViewModel", "Producto añadido al vendedor con éxito")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al agregar producto al vendedor: ${e.localizedMessage}")
            }
        }
    }

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    fun onValidationError(message: String) {
        _errorState.postValue(message)
    }

    fun onImageUploadError(message: String) {
        _errorState.postValue("Error al subir la imagen: $message")
    }

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
}