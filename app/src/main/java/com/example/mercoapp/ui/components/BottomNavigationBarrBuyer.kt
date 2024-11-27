package com.example.mercoapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.mercoapp.Routes
import com.example.mercoapp.Routes.CartBuyer
import com.example.mercoapp.Routes.FavoriteStoresBuyer
import com.example.mercoapp.Routes.HomeBuyer
import com.example.mercoapp.Routes.StoreBuyer
import com.example.mercoapp.Routes.UserProfileBuyer
import com.example.mercoapp.viewModel.SharedUserViewModel

@Composable
fun BottomNavigationBarrBuyer(
    navController: NavController?,
    currentScreen: String,
    sharedUserViewModel: SharedUserViewModel
) {
    val buyer by sharedUserViewModel.buyer.observeAsState() // Observa el comprador desde el ViewModel

    NavigationBar {
        // Inicio
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentScreen == "Inicio",
            onClick = {
                buyer?.let { // Asegúrate de que buyer no sea nulo
                    navController?.navigate("${Routes.HomeBuyer}/${it.id}") {
                        popUpTo("${Routes.HomeBuyer}/{buyerId}") { inclusive = true }
                    }
                }
            }
        )

        // Tienda
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Tienda") },
            label = { Text("Tienda") },
            selected = currentScreen == "Tienda",
            onClick = { navController?.navigate(Routes.StoreBuyer) }
        )

        // Carrito
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            selected = currentScreen == "Carrito",
            onClick = { navController?.navigate(Routes.CartBuyer) }
        )

        // Favoritos
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = currentScreen == "Favoritos",
            onClick = { navController?.navigate(Routes.FavoriteStoresBuyer) }
        )

        // Perfil
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentScreen == "Perfil",
            onClick = {
                buyer?.let { // Asegúrate de que buyer no sea nulo
                    navController?.navigate("${Routes.UserProfileBuyer}/${it.id}") {
                        popUpTo("${Routes.UserProfileBuyer}/{buyerId}") { inclusive = true }
                    }
                }
            }
        )
    }
}

