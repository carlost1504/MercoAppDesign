package com.example.mercoapp.domain.model

data class UserBuyer (

        var id:String = "",
        var name: String = "",
        var lastName: String = "",
        var typeDocument: String="",
        var document: String = "",
        var email: String = "",
        var cell: String = "",
        var profilePhotoUri: String = "", // URL o URI de la foto de perfil
        var password:String="",
)
