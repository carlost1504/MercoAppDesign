package com.example.mercoapp.ui.pages.buyer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.ui.components.DiscountCard
import com.example.mercoapp.ui.components.ProductList
import com.example.mercoapp.viewModel.SharedUserViewModel
import com.example.mercoapp.ui.components.TabRow
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun HomeScreenBuyer(
    modifier: Modifier = Modifier,
    navController: NavController,
    sharedUserViewModel: SharedUserViewModel
) {
    // Observa el comprador desde el ViewModel compartido
    val buyer by sharedUserViewModel.buyer.observeAsState()
    val isLoading by sharedUserViewModel.isLoading.observeAsState(false)

    // Cargar datos del comprador al iniciar la pantalla
    LaunchedEffect(Unit) {
        val buyerId = Firebase.auth.currentUser?.uid
        if (buyerId != null) {
            sharedUserViewModel.loadBuyerData(buyerId)
        } else {
            Log.e("HomeScreenBuyer", "Error: UID del comprador no encontrado.")
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBarrBuyer(
                navController = navController,
                currentScreen = "Inicio",
                sharedUserViewModel = sharedUserViewModel
            )
        }
    ) { padding ->
        if (isLoading) {
            // Muestra un indicador de carga si está cargando los datos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Bienvenid@ ${buyer?.name ?: "Usuario"} a Merco",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TabRow()
                DiscountCard()
                ProductList()
            }
        }
    }
}















