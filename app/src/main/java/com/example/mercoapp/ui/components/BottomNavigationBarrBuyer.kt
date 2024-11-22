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
import androidx.navigation.NavController
import com.example.mercoapp.Routes.CartBuyer
import com.example.mercoapp.Routes.FavoriteStoresBuyer
import com.example.mercoapp.Routes.HomeBuyer
import com.example.mercoapp.Routes.StoreBuyer
import com.example.mercoapp.Routes.UserProfileBuyer

@Composable
fun BottomNavigationBarr(navController: NavController?,currentScreen: String) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentScreen == "Inicio",
            onClick = { navController?.navigate(HomeBuyer) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Tienda") },
            label = { Text("Tienda") },
            selected = currentScreen == "Tienda",
            onClick = { navController?.navigate(StoreBuyer) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            selected = currentScreen == "Carrito",
            onClick = { navController?.navigate(CartBuyer) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = currentScreen == "Favoritos",
            onClick = { navController?.navigate(FavoriteStoresBuyer) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentScreen == "Perfil",
            onClick = { navController?.navigate(UserProfileBuyer) }
        )
    }
}
