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
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.ui.components.BottomNavigationBarr
import com.example.mercoapp.ui.components.ProductCard
import com.example.mercoapp.viewModel.ProductViewModel
import com.example.mercoapp.viewModel.UserViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProductsScreen(
    navController: NavController?,
    productViewModel: ProductViewModel = viewModel(),
    sellerId: String
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Activos", "No activos", "Todos")

    // Observar productos del vendedor
    val sellerProducts by productViewModel.sellerProducts.observeAsState(emptyList())
    val isLoading by productViewModel.isLoading.observeAsState(false)

    // Cargar productos al inicio
    LaunchedEffect(Unit) {
        productViewModel.getProductsBySeller(sellerId)
    }

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
                onClick = { navController?.navigate("createProduct") },
                containerColor = Color(0xFFFF6D00)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Producto")
            }
        },
        bottomBar = {
            BottomNavigationBarr(navController, "Inicio")
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

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.Gray
                )
            } else {
                // Mostrar productos filtrados
                val filteredProducts = sellerProducts.filter {
                    when (selectedTabIndex) {
                        0 -> it.isActive // Activos
                        1 -> !it.isActive // No activos
                        else -> true // Todos
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            onDeleteClick = { /* Agrega lógica para eliminar el producto */ }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun SellerProductsScreenPreview() {
    // Crear un NavController de prueba
    val navController = rememberNavController()

    // Crear un ProductViewModel simulado con datos de prueba
    val fakeProductViewModel = object : ProductViewModel() {
        private val _sellerProducts = MutableLiveData<List<Product>>(
            listOf(
                Product(
                    id = "1",
                    name = "Producto Activo 1",
                    description = "Descripción del producto activo 1",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 25.0,
                    variety = "Vainilla",
                    isActive = true
                ),
                Product(
                    id = "2",
                    name = "Producto Inactivo 1",
                    description = "Descripción del producto inactivo 1",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 30.0,
                    variety = "Chocolate",
                    isActive = false
                )
            )
        )

        override val sellerProducts: MutableLiveData<List<Product>> = _sellerProducts
        override val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    // Simula un ID de vendedor
    val mockSellerId = "seller12345"

    // Llama a la función principal de la pantalla
    SellerProductsScreen(
        navController = navController,
        productViewModel = fakeProductViewModel,
        sellerId = mockSellerId
    )
}

