package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.ui.components.CartItem
import com.example.mercoapp.viewModel.SharedUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenBuyer(
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi carrito de pedidos", style = MaterialTheme.typography.titleMedium) },
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Lista de artículos en el carrito
            CartItem(
                itemName = "Torta + Helado",
                itemPrice = "20.000 $",
                imageUrl = "https://via.placeholder.com/150" // URL de ejemplo
            )

            // Botón de verificación
            Button(
                onClick = { /* Acción de verificación */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("VERIFICAR", color = Color.White)
            }
        }
    }
}




@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CartScreenPreview() {
    // Simulamos el NavController para la previsualización
    val navController = rememberNavController()

    CartScreenBuyer(navController = navController,sharedUserViewModel= SharedUserViewModel())
}