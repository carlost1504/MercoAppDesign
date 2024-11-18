package com.example.mercoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mercoapp.ui.pages.buyer.HomePage
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.seller.SignupPageSeller
import com.example.mercoapp.ui.pages.buyer.SignupPageBuyer
import com.example.mercoapp.ui.pages.buyer.StoreScreen
import com.example.mercoapp.ui.pages.TypeUserSignup
import com.example.mercoapp.ui.pages.buyer.CartScreen
import com.example.mercoapp.ui.pages.buyer.OrderConfirmationScreen
import com.example.mercoapp.ui.pages.buyer.OrderDetailScreen
import com.example.mercoapp.ui.pages.buyer.OrderHistoryScreen
import com.example.mercoapp.ui.pages.buyer.ProductDetailsScreen
import com.example.mercoapp.ui.pages.buyer.UserProfileScreen
import com.example.mercoapp.ui.pages.seller.CreateProductPage
import com.example.mercoapp.ui.pages.seller.SellerProductsScreen
import com.example.mercoapp.ui.pages.seller.UserSellerProfileScreen
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.viewModel.ProductViewModel
import com.example.mercoapp.viewModel.UserViewModel


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mercoInit") {

        // Grupo de autenticación y registro
        authNavigation(navController, authViewModel)

        // Grupo de usuario
        userNavigation(navController)

        // Grupo de productos y tienda
        productAndStoreNavigation(navController)

        // Grupo de carrito y pedidos
        cartAndOrderNavigation(navController)
    }
}

// Funciones auxiliares para organizar las rutas por grupos
private fun NavGraphBuilder.authNavigation(navController: NavController, authViewModel: AuthViewModel) {
    composable("mercoInit") {
        MercoInit(navController = navController, authViewModel = authViewModel)
    }
    composable("login") {
        LoginPage(navController = navController, authViewModel = authViewModel)
    }
    composable("typeUser") {
        TypeUserSignup(navController = navController)
    }
    composable("signupBuyer") {
        SignupPageBuyer(navController = navController, authViewModel = authViewModel)
    }
    composable("signupSeller") {
        SignupPageSeller(navController = navController, authViewModel = authViewModel)
    }
}

private fun NavGraphBuilder.userNavigation(navController: NavController) {
    composable("infoUser") {
        UserProfileScreen(navController = navController)
    }
    composable("userProfile") {
        UserProfileScreen(navController = navController)
    }
}

private fun NavGraphBuilder.productAndStoreNavigation(navController: NavController) {
    composable("home") {
        HomePage(navController = navController)
    }
    composable("store") {
        StoreScreen(navController = navController)
    }
    composable("createProduct") {
        CreateProductPage(navController = navController, sellerId = "exampleSellerId") // Usa el ID real
    }
    composable("productDetails") {
        ProductDetailsScreen(navController = navController)
    }
    composable("sellerProducts/{sellerId}") { backStackEntry ->
        val sellerId = backStackEntry.arguments?.getString("sellerId") ?: return@composable
        val productViewModel: ProductViewModel = viewModel()

        SellerProductsScreen(
            navController = navController,
            productViewModel = productViewModel,
            sellerId = sellerId
        )
    }

    composable("userSellerProfile/{sellerId}") { backStackEntry ->
        val sellerId = backStackEntry.arguments?.getString("sellerId") ?: return@composable
        val userViewModel: UserViewModel = viewModel()

        UserSellerProfileScreen(
            navController = navController,
            userViewModel = userViewModel
        )
    }
}

private fun NavGraphBuilder.cartAndOrderNavigation(navController: NavController) {
    composable("cart") {
        CartScreen(navController = navController)
    }
    composable("orderHistory") {
        OrderHistoryScreen(navController = navController)
    }
    composable("orderDetails") {
        OrderDetailScreen(navController = navController)
    }
    composable("orderConfirmation") {
        OrderConfirmationScreen(navController = navController)
    }
}

