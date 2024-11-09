package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mercoapp.ui.components.BottomNavigationBarr
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(navController: NavController?) {
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
            BottomNavigationBarr(navController, "Carrito")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                AddressCard(
                    title = "Dirección Tienda Elegida",
                    address = "Ciudad Jardín\nDirección: Carrera 103 # 13 - 20",
                    changeable = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                AddressCard(
                    title = "Dirección Tienda Recomendada",
                    address = "Ciudad Jardín\nDirección: Carrera 66 # 13 - 20"
                )
                Spacer(modifier = Modifier.height(16.dp))
                OrderSummary(orderTotal = "25.000 $", summaryTotal = "25.000 $")
            }

            Button(
                onClick = { /* Acción para reservar pedido */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("RESERVAR PEDIDO", color = Color.White)
            }
        }
    }
}

@Composable
fun AddressCard(title: String, address: String, changeable: Boolean = false) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = address, style = MaterialTheme.typography.bodyMedium)
            if (changeable) {
                Text(
                    text = "Cambiar",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { /* Acción para cambiar dirección */ }
                )
            }
        }
    }
}

@Composable
fun OrderSummary(orderTotal: String, summaryTotal: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Orden:", style = MaterialTheme.typography.bodyMedium)
            Text(orderTotal, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Resumen:", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(summaryTotal, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun OrderConfirmationScreenPreview() {
    // Simulamos el NavController para la previsualización
    val navController = rememberNavController()

    OrderConfirmationScreen(navController = navController)
}