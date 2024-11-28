package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.ui.components.GreetingSection
import com.example.mercoapp.ui.components.ProductGrid
import com.example.mercoapp.viewModel.ProductViewModel
import com.example.mercoapp.viewModel.SharedUserViewModel
import com.example.mercoapp.ui.components.SearchBar



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreenBuyer(
    modifier: Modifier = Modifier,
    navController: NavController?,
    productViewModel: ProductViewModel = viewModel(),
    sharedUserViewModel: SharedUserViewModel
) {
    // Observa los productos activos desde el ViewModel de productos
    val products by productViewModel.sellerProducts.observeAsState(emptyList())
    val isLoading by productViewModel.isLoading.observeAsState(false)

    // Carga los productos activos al inicio
    LaunchedEffect(Unit) {
        productViewModel.getAllActiveProducts()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tienda", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarrBuyer(
                navController = navController,
                currentScreen = "Tienda",
                sharedUserViewModel = sharedUserViewModel
            )
        }
    ) { padding ->
        if (isLoading) {
            // Mostrar indicador de carga
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (products.isEmpty()) {
                // Mostrar mensaje si no hay productos disponibles
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay productos disponibles.")
                }
            } else {
                // Mostrar productos usando tu componente ProductGrid
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    ProductGrid(products = products)
                }
            }
        }
    }
}
















