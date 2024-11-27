package com.example.mercoapp.ui.pages.buyer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.R
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.viewModel.SharedUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteStoresScreenBuyer(

    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
    ) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tiendas favoritas", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de retroceso */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarrBuyer(navController, "Favoritos", sharedUserViewModel)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FavoriteStoresList()
        }
    }
}

@Composable
fun FavoriteStoresList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(5) { // Cambia el número de elementos o usa una lista dinámica
            FavoriteStoreItem(
                storeName = "Ventolini",
                onProductClick = { /* Acción al ver productos */ },
                onDeleteClick = { /* Acción al eliminar tienda */ }
            )
        }
    }
}

@Composable
fun FavoriteStoreItem(
    storeName: String,
    onProductClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Imagen del logotipo de la tienda
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplaza con la imagen del logotipo
                contentDescription = "Logo de la tienda",
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray, shape = CircleShape) // Placeholder para la imagen
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la tienda
            Column(modifier = Modifier.weight(1f)) {
                Text(text = storeName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Button(
                    onClick = onProductClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // Color verde
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Ver Productos", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Botón de eliminar
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar tienda", tint = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteStoresScreenPreview() {
    FavoriteStoresScreenBuyer(navController = rememberNavController(),sharedUserViewModel=SharedUserViewModel())
}
