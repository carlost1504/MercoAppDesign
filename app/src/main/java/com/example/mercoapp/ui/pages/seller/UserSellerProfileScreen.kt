package com.example.mercoapp.ui.pages.seller


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.mercoapp.Routes.MercoInit
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.BottomNavigationBarrSeller
import com.example.mercoapp.ui.components.CustomTextFieldDisplay
import com.example.mercoapp.viewModel.SharedUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreenSeller(
    modifier: Modifier = Modifier,
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    // Observar los datos del vendedor desde el SharedUserViewModel
    val seller by sharedUserViewModel.seller.observeAsState()
    val sellerState = when {
        seller != null -> 3 // Éxito
        sharedUserViewModel.isLoading.value == true -> 1 // Cargando
        else -> 2 // Error
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Perfil del Vendedor", style = MaterialTheme.typography.h6) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    // Botón para salir
                    IconButton(onClick = {
                        sharedUserViewModel.clearSellerData() // Limpia los datos del vendedor
                        navController?.navigate(MercoInit) {
                            popUpTo(0) // Elimina todo el stack de navegación
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarrSeller(
                navController = navController,
                currentScreen = "Perfil",
                sharedUserViewModel = sharedUserViewModel // Pasamos el ViewModel
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            when (sellerState) {
                1 -> { // Estado de carga
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.Gray
                        )
                        Text(
                            text = "Cargando datos del usuario...",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                2 -> { // Estado de error
                    item {
                        Text(
                            text = "Error al cargar datos del usuario.",
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
                3 -> { // Estado de éxito con datos del vendedor
                    seller?.let { currentSeller ->
                        item {
                            SellerDetails(seller = currentSeller, modifier)
                        }
                    }
                }
                else -> { // Estado inicial o sin datos
                    item {
                        Text(
                            text = "No se encontraron datos del usuario.",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SellerDetails(seller: UserSeller, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Información básica del vendedor
        CustomTextFieldDisplay(
            value = seller.name.ifEmpty { "Nombre no disponible" },
            label = "Nombre",
        )
        CustomTextFieldDisplay(
            value = seller.lastName.ifEmpty { "Apellido no disponible" },
            label = "Apellido",
        )
        CustomTextFieldDisplay(
            value = seller.documentTypes.ifEmpty { "Tipo de Documento no disponible" },
            label = "Tipo de Documento",
        )
        CustomTextFieldDisplay(
            value = seller.document.ifEmpty { "Documento no disponible" },
            label = "Documento",
        )
        CustomTextFieldDisplay(
            value = seller.email.ifEmpty { "Correo electrónico no disponible" },
            label = "Correo Electrónico",
        )
        CustomTextFieldDisplay(
            value = seller.cell.ifEmpty { "Número de celular no disponible" },
            label = "Celular",
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Información de la tienda
        Text(
            text = "Información de la Tienda",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        CustomTextFieldDisplay(
            value = seller.nameStore.ifEmpty { "Nombre de la tienda no disponible" },
            label = "Nombre de la Tienda",
        )
        CustomTextFieldDisplay(
            value = seller.storeAddress.ifEmpty { "Dirección de la tienda no disponible" },
            label = "Dirección de la Tienda",
        )
        CustomTextFieldDisplay(
            value = seller.storeDescription.ifEmpty { "Descripción de la tienda no disponible" },
            label = "Descripción de la Tienda",
        )
    }
}




