package com.example.mercoapp

object Routes {


    // General
    const val MercoInit = "mercoInit"
    const val Login = "login"
    const val TypeUser = "typeUser"

    // Buyer
    const val HomeBuyer = "home_buyer"
    const val CartBuyer = "cart_buyer"
    const val CategoriesBuyer = "categories_buyer"
    const val FavoriteStoresBuyer = "favorite_stores_buyer"
    const val OrderHistoryBuyer = "order_history_buyer"
    const val ProductDetailsBuyer = "product_details_buyer"
    const val OrderDetailBuyer = "order_detail_buyer/{orderId}" // Ruta con argumento dinámico
    const val OrderConfirmationBuyer = "order_confirmation_buyer"
    const val StoreBuyer = "store_buyer"
    const val UserProfileBuyer = "user_profile_buyer"

    // Seller
    const val HomeSeller = "homeSeller"
    const val CreateProductSeller = "createProduct"
    const val SigupSeller= "signupSeller"
    const val UserProfileSeller = "userProfileSeller"
}
