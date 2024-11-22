package com.example.mercoapp.domain.model

data class UserSeller(
    var id: String = "",
    var name: String = "",
    var lastName: String = "",
    var documentTypes: String = "",
    var document: String = "",
    var email: String = "",
    var cell: String = "",
    var profilePhotoUri: String = "",
    var password: String = "",
    var nameStore: String = "",
    var storeAddress: String = "",
    var storePhotoUri: String = "",
    var storeDescription: String = "",
    var addressShops: String = "",
    var productIds: List<Product> = mutableListOf() // Array los productos
)