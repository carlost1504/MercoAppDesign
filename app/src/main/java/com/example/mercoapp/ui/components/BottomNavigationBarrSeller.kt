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
import com.example.mercoapp.Routes.CreateProductSeller
import com.example.mercoapp.Routes.HomeSeller
import com.example.mercoapp.Routes.UserProfileSeller
import com.example.mercoapp.viewModel.SharedUserViewModel

@Composable
fun BottomNavigationBarrSeller(navController: NavController?,
                               currentScreen: String,
                               sharedUserViewModel: SharedUserViewModel
) {
    val seller by sharedUserViewModel.seller.observeAsState()

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentScreen == "Inicio",
            onClick = {
                seller?.let { // Asegúrate de que seller no sea nulo
                    navController?.navigate("${Routes.HomeSeller}/${it.id}") {
                        popUpTo("${Routes.HomeSeller}/{sellerId}") { inclusive = true }
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = currentScreen == "Favoritos",
            onClick = { navController?.navigate(Routes.CreateProductSeller) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentScreen == "Perfil",
            onClick = { navController?.navigate(Routes.UserProfileSeller) }
        )
    }
}
