package com.example.mercoapp.ui.pages.buyer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.R
import com.example.mercoapp.Routes
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.viewModel.SharedUserViewModel
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreenBuyer(
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    // Observa los productos seleccionados (o el carrito) desde el ViewModel
    val selectedProducts by sharedUserViewModel.selectedProducts.observeAsState(emptyList())
    val quantities by sharedUserViewModel.productQuantities.observeAsState(mapOf())

    // Calcula el total de la orden
    val totalAmount = selectedProducts.sumOf { it.price * (quantities[it.id] ?: 1) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles del Pedido", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarrBuyer(navController, "Perfil", sharedUserViewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Encabezado del Pedido
            OrderHeader(
                itemsCount = selectedProducts.size,
                totalAmount = totalAmount
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Artículos del Pedido
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedProducts) { product ->
                    OrderItemDetail(product = product, quantity = quantities[product.id] ?: 1)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para confirmar el pedido
            Button(
                onClick = {
                    val selectedProducts = sharedUserViewModel.selectedProducts.value ?: emptyList()
                    val quantities = sharedUserViewModel.productQuantities.value ?: emptyMap()

                    if (selectedProducts.isNotEmpty() && quantities.isNotEmpty()) {
                        sharedUserViewModel.createOrder() // Crea la orden usando los datos internos
                        navController?.navigate(Routes.OrderConfirmationBuyer) // Navega a la pantalla de confirmación
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Confirmar Pedido", color = Color.White)
            }


        }
    }
}

@Composable
fun OrderHeader(itemsCount: Int, totalAmount: Double) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Resumen del Pedido", fontWeight = FontWeight.Bold)
            Text(text = "Total: $${"%.2f".format(totalAmount)}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$itemsCount elementos", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun OrderItemDetail(product: Product, quantity: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = product.imageUrl),
                contentDescription = "Imagen del producto",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = "Variedad: ${product.variety}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = "Unidades: $quantity", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "$${"%.2f".format(product.price * quantity)}", fontWeight = FontWeight.Bold, color = Color.Gray)
        }
    }
}





