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
import com.example.mercoapp.Routes.CreateProductSeller
import com.example.mercoapp.Routes.HomeSeller
import com.example.mercoapp.Routes.UserProfileSeller

@Composable
fun BottomNavigationBarrSeller(navController: NavController?, currentScreen: String) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentScreen == "Inicio",
            onClick = { navController?.navigate(HomeSeller) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = currentScreen == "Favoritos",
            onClick = { navController?.navigate(CreateProductSeller) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentScreen == "Perfil",
            onClick = { navController?.navigate(UserProfileSeller) }
        )
    }
}