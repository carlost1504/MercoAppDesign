package com.example.mercoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.TypeUserSignup
import com.example.mercoapp.ui.pages.buyer.CartScreenBuyer
import com.example.mercoapp.ui.pages.buyer.CategoriesScreenBuyer
import com.example.mercoapp.ui.pages.buyer.FavoriteStoresScreenBuyer
import com.example.mercoapp.ui.pages.buyer.HomeScreenBuyer
import com.example.mercoapp.ui.pages.buyer.OrderConfirmationScreenBuyer
import com.example.mercoapp.ui.pages.buyer.OrderDetailScreenBuyer
import com.example.mercoapp.ui.pages.buyer.OrderHistoryScreenBuyer
import com.example.mercoapp.ui.pages.buyer.ProductDetailsScreenBuyer
import com.example.mercoapp.ui.pages.buyer.StoreScreenBuyer
import com.example.mercoapp.ui.pages.buyer.UserProfileScreenBuyer
import com.example.mercoapp.ui.pages.seller.CreateProductPageSeller
import com.example.mercoapp.ui.pages.seller.SellerProductsScreenSeller
import com.example.mercoapp.ui.pages.seller.SignupPageSeller
import com.example.mercoapp.ui.pages.seller.UserProfileScreenSeller
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.viewModel.SharedUserViewModel


@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val sharedUserViewModel: SharedUserViewModel = viewModel() // Instancia del SharedUserViewModel

    NavHost(
        navController = navController,
        startDestination = Routes.MercoInit
    ) {
        composable(Routes.MercoInit) {
            MercoInit(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        composable(Routes.Login) {
            LoginPage(
                navController = navController,
                authViewModel = authViewModel,
                sharedUserViewModel = sharedUserViewModel
            )
        }
        composable(Routes.TypeUser) {
            TypeUserSignup(
                navController = navController
            )
        }

        // Subgrafo de Buyer
        buyerNavGraph(navController)

        // Subgrafo de Seller
        sellerNavGraph(navController, sharedUserViewModel) // Pasamos el ViewModel compartido
    }
}

fun NavGraphBuilder.buyerNavGraph(navController: NavController) {
    navigation(startDestination = Routes.HomeBuyer, route = "buyer") {
        composable(Routes.HomeBuyer) {
            HomeScreenBuyer(navController = navController)
        }
        composable(Routes.CartBuyer) {
            CartScreenBuyer(navController = navController)
        }
        composable(Routes.CategoriesBuyer) {
            CategoriesScreenBuyer()
        }
        composable(Routes.FavoriteStoresBuyer) {
            FavoriteStoresScreenBuyer(navController = navController)
        }
        composable(Routes.OrderHistoryBuyer) {
            OrderHistoryScreenBuyer(navController = navController)
        }
        composable(Routes.ProductDetailsBuyer) {
            ProductDetailsScreenBuyer(navController = navController)
        }
        composable(Routes.OrderDetailBuyer) {
            OrderDetailScreenBuyer(navController = navController)
        }
        composable(Routes.OrderConfirmationBuyer) {
            OrderConfirmationScreenBuyer(navController = navController)
        }
        composable(Routes.StoreBuyer) {
            StoreScreenBuyer(navController = navController)
        }
        composable(Routes.UserProfileBuyer) {
            UserProfileScreenBuyer(navController = navController)
        }
    }
}

fun NavGraphBuilder.sellerNavGraph(
    navController: NavController,
    sharedUserViewModel: SharedUserViewModel
) {
    navigation(startDestination = "${Routes.HomeSeller}/{sellerId}", route = "seller") {
        // Ruta dinámica para HomeSeller con sellerId
        composable(
            route = "${Routes.HomeSeller}/{sellerId}",
            arguments = listOf(navArgument("sellerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sellerId = backStackEntry.arguments?.getString("sellerId")!!
            sharedUserViewModel.loadSellerData(sellerId) // Carga los datos del vendedor
            SellerProductsScreenSeller(
                navController = navController,
                sharedUserViewModel = sharedUserViewModel
            )
        }

        // Ruta para crear productos
        composable(Routes.CreateProductSeller) {
            CreateProductPageSeller(
                navController = navController,
                sharedUserViewModel = sharedUserViewModel
            )
        }

        // Ruta para el registro de vendedor
        composable(Routes.SigupSeller) {
            SignupPageSeller(navController = navController)
        }

        // Ruta para el perfil del vendedor
        composable(Routes.UserProfileSeller) {
            UserProfileScreenSeller(
                navController = navController,
                sharedUserViewModel = sharedUserViewModel
            )
        }
    }
}





