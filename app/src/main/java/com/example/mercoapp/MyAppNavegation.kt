package com.example.mercoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.TypeUserSignup
import com.example.mercoapp.ui.pages.buyer.CartScreenBuyer
import com.example.mercoapp.ui.pages.buyer.CategoriesScreenBuyer
import com.example.mercoapp.ui.pages.buyer.FavoriteStoresScreenBuyer
import com.example.mercoapp.ui.pages.buyer.HomePageBuyer
import com.example.mercoapp.ui.pages.buyer.OrderHistoryScreenBuyer
import com.example.mercoapp.ui.pages.buyer.ProductDetailsScreenBuyer
import com.example.mercoapp.ui.pages.buyer.SignupPageBuyer
import com.example.mercoapp.ui.pages.seller.CreateProductPageSeller
import com.example.mercoapp.ui.pages.seller.SellerProductsScreenSeller
import com.example.mercoapp.ui.pages.seller.SignupPageSeller
import com.example.mercoapp.ui.pages.seller.UserProfileScreenSeller
import com.example.mercoapp.viewModel.AuthViewModel


@Composable
fun MainNavGraph(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MercoInit) {
        composable(Routes.MercoInit) {
            MercoInit(navController = navController, authViewModel = authViewModel)
        }
        composable(Routes.Login) {
            LoginPage(navController = navController, authViewModel = authViewModel)
        }
        composable(Routes.TypeUser) {
            TypeUserSignup(navController = navController)
        }

        // Subgrafo de Buyer
        buyerNavGraph(navController)
        // Subgrafo de Seller
        sellerNavGraph(navController)
    }
}

fun NavGraphBuilder.buyerNavGraph(navController: NavController) {
    navigation(startDestination = Routes.HomeBuyer, route = "buyer") {
        composable(Routes.HomeBuyer) {
            HomePageBuyer(navController = navController)
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
    }
}

fun NavGraphBuilder.sellerNavGraph(navController: NavController) {
    navigation(startDestination = Routes.HomeSeller, route = "seller") {
        composable(Routes.HomeSeller) {
            SellerProductsScreenSeller(navController = navController, sellerId = "mockSellerId")
        }
        composable(Routes.CreateProductSeller) {
            CreateProductPageSeller(navController = navController, sellerId = "mockSellerId")
        }
        composable(Routes.SellerProducts) {
            SellerProductsScreenSeller(navController = navController, sellerId = "mockSellerId")
        }
        composable(Routes.UserProfileSeller) {
            UserProfileScreenSeller(navController = navController, userViewModel = viewModel())
        }
    }
}



