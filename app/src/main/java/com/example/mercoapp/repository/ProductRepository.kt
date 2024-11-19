package com.example.mercoapp.repository

import android.net.Uri
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.service.ProductService
import com.example.mercoapp.service.ProductServiceImpl

interface ProductRepository {
    suspend fun createProduct(product: Product)
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(productId: String): Product?
    suspend fun uploadProductImage(uri: Uri): String // Método para subir imágenes
}

class ProductRepositoryImpl(
    private val productService: ProductService = ProductServiceImpl()
) : ProductRepository {
    override suspend fun createProduct(product: Product) {
        productService.createProduct(product)
    }

    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }

    override suspend fun getProductById(productId: String): Product? {
        return productService.getProductById(productId)
    }

    override suspend fun uploadProductImage(uri: Uri): String {
        return productService.uploadProductImage(uri)
    }
}