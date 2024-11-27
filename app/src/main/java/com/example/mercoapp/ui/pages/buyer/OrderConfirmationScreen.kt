package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.Routes
import com.example.mercoapp.ui.components.AddressCard
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.ui.components.OrderSummary
import com.example.mercoapp.viewModel.SharedUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreenBuyer(
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    // Observa la orden actual desde el ViewModel
    val currentOrder by sharedUserViewModel.currentOrder.observeAsState()

    // Actualiza el estado del pedido a "INPROGRESS" al entrar a esta pantalla
    LaunchedEffect(currentOrder) {
        currentOrder?.let { order ->
            sharedUserViewModel.updateOrderStatus(order.id, "INPROGRESS")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Confirmación de pedido", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarrBuyer(navController, "Carrito", sharedUserViewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentOrder != null) {
                // Mostrar detalles de la orden
                Column {
                    AddressCard(
                        title = "Dirección Tienda Elegida",
                        address = "No especificada", // Usa un valor predeterminado
                        changeable = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AddressCard(
                        title = "Dirección Tienda Recomendada",
                        address = "No especificada" // Usa un valor predeterminado
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OrderSummary(
                        orderTotal = "${currentOrder!!.totalPrice} $",
                        summaryTotal = "${currentOrder!!.totalPrice} $"
                    )
                }

                Button(
                    onClick = { navController?.navigate(Routes.HomeBuyer) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("VER HISTORIAL DE PEDIDOS", color = Color.White)
                }
            } else {
                // Mostrar un mensaje si no hay orden actual
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay información de la orden actual.")
                }
            }
        }

    }
}







@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun OrderConfirmationScreenPreview() {
    // Simulamos el NavController para la previsualización
    val navController = rememberNavController()

    OrderConfirmationScreenBuyer(navController = navController,sharedUserViewModel= SharedUserViewModel())
}