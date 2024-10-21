package com.example.mercoapp.domain.model

data class UserSeller (

    var id:String = "",
    val name: String = "",
    val lastName: String = "",
    val documentTypes :String="",
    val document: String = "",
    val email: String = "",
    val cell: String = "",
    val profilePhotoUri: String = "", // URL o URI de la foto de perfil
    val nameStore: String = "", // Nombre de la tienda
    val storeAddress: String = "", // Dirección de la tienda
    val storePhotoUri: String = "", // URL o URI de la foto de la tienda
    val storeDescription: String = "", // Descripción de la tienda
    val addressShops: String = "", // Dirección de otros locales (si aplica)
    var password:String="",


)