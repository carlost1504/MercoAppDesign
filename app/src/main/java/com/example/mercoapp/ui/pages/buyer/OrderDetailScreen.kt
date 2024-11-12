package com.example.mercoapp.ui.pages.buyer

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
import com.example.mercoapp.ui.components.BottomNavigationBarr
import com.example.mercoapp.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(navController: NavController?) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles de pedido", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarr(navController, "Perfil")
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
                orderNumber = "N°1947034",
                date = "05-12-2019",
                status = "Recogido",
                itemsCount = 3
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Artículos del Pedido
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(getSampleOrderItems()) { item ->
                    OrderItemDetail(item)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional del pedido
            OrderInfoSection(
                deliveryAddresses = listOf(
                    "Dirección: Carrera 103 # 13 - 20",
                    "Dirección: Carrera 122 # 16 - 10"
                ),
                totalAmount = "133$"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para Comentarios
            Button(
                onClick = { /* Acción para agregar comentario */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Comentario", color = Color.White)
            }
        }
    }
}

@Composable
fun OrderHeader(orderNumber: String, date: String, status: String, itemsCount: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Orden $orderNumber", fontWeight = FontWeight.Bold)
            Text(text = date, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "$itemsCount elementos", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = status, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OrderItemDetail(item: OrderItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = item.imageUrl),
                contentDescription = "Imagen del producto",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = "Variedad: ${item.variety}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = "Unidades: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = item.price, fontWeight = FontWeight.Bold, color = Color.Gray)
        }
    }
}

@Composable
fun OrderInfoSection(deliveryAddresses: List<String>, totalAmount: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Información del pedido", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        deliveryAddresses.forEach { address ->
            Text(text = address, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Monto total: $totalAmount", fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}

// Modelo de datos para los artículos de ejemplo
data class OrderItem(
    val name: String,
    val variety: String,
    val quantity: Int,
    val price: String,
    val imageUrl: String
)

// Ejemplo de datos de los artículos del pedido
fun getSampleOrderItems(): List<OrderItem> {
    return listOf(
        OrderItem(name = "Torta + Helado", variety = "Vainilla", quantity = 1, price = "30.000", imageUrl = "https://via.placeholder.com/150"),
        OrderItem(name = "Porción cheesecake", variety = "Fresa", quantity = 1, price = "11.000", imageUrl = "https://via.placeholder.com/150"),
        OrderItem(name = "Empanadas y aborrajados", variety = "Mixta", quantity = 1, price = "5.5$", imageUrl = "https://via.placeholder.com/150")
    )
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun OrderDetailScreenPreview() {
    // Simulamos el NavController para la previsualización
    val navController = rememberNavController()

    OrderDetailScreen(navController = navController)
}
