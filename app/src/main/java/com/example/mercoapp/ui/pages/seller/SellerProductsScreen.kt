package com.example.mercoapp.ui.pages.seller

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.R
import com.example.mercoapp.Routes.CreateProductSeller
import com.example.mercoapp.ui.components.BottomNavigationBarrSeller
import com.example.mercoapp.ui.components.ProductCard
import com.example.mercoapp.viewModel.SharedUserViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProductsScreenSeller(
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Activos", "No activos", "Todos")

    // Observar los datos del vendedor desde el SharedUserViewModel
    val seller by sharedUserViewModel.seller.observeAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis productos", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController?.navigate(CreateProductSeller) },
                containerColor = Color(0xFFFF6D00)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        },
        bottomBar = {
            BottomNavigationBarrSeller(navController, "Inicio", sharedUserViewModel = sharedUserViewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // TabRow para filtrar productos
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            // Verificar si el vendedor tiene productos
            seller?.let { currentSeller ->
                val filteredProducts = currentSeller.productIds.filter {
                    when (selectedTabIndex) {
                        0 -> it.isActive // Activos
                        1 -> !it.isActive // No activos
                        else -> true // Todos
                    }
                }

                if (filteredProducts.isEmpty()) {
                    // Mostrar mensaje si no hay productos
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay productos disponibles.")
                    }
                } else {
                    // Mostrar productos en un LazyVerticalGrid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredProducts) { product ->
                            ProductCard(
                                product = product,
                                onDeleteClick = {
                                    sharedUserViewModel.removeProductFromSeller(product)
                                }
                            )
                        }
                    }
                }
            } ?: run {
                // Mostrar indicador de carga mientras se obtienen los datos
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



