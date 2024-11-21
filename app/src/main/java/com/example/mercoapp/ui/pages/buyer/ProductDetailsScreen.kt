package com.example.mercoapp.ui.pages.buyer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ProductDetailsScreenBuyer(navController: NavController?) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cake Red Velvet", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción de compartir */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarr(navController, "Producto")
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                ProductImageSection(imageUrl = "https://via.placeholder.com/150") // URL de ejemplo
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ProductInfoSection(
                    title = "Cake Red Velvet",
                    price = "20.000 $",
                    description = "Capas de bizcocho red velvet, cubiertas con cobertura de queso crema."
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                ExpandableSection(title = "Detalles del artículo") {
                    Text("Información sobre el tamaño, ingredientes, etc.")
                }
                Spacer(modifier = Modifier.height(8.dp))

                ExpandableSection(title = "Información de la tienda") {
                    Text("Detalles de la tienda, ubicación y políticas.")
                }
                Spacer(modifier = Modifier.height(8.dp))

                ExpandableSection(title = "Reseñas de la marca") {
                    Text("Opiniones y calificaciones de otros usuarios.")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                RecommendedProductsSection()
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = { /* Acción para agregar al carrito */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Agregar a carrito", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProductImageSection(imageUrl: String) {
    Image(
        painter = rememberImagePainter(data = imageUrl),
        contentDescription = "Imagen del producto",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    )
}

@Composable
fun ProductInfoSection(title: String, price: String, description: String) {
    Column {
        Text(text = title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded) "Colapsar" else "Expandir"
            )
        }
        if (expanded) {
            content()
        }
    }
}

@Composable
fun RecommendedProductsSection() {
    Column {
        Text(text = "También te puede gustar esto", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(3) { // Puedes usar una lista dinámica aquí
                RecommendedProductItem(
                    imageUrl = "https://via.placeholder.com/150",
                    productName = "Torta + Helado",
                    productPrice = "24.000 $"
                )
            }
        }
    }
}

@Composable
fun RecommendedProductItem(imageUrl: String, productName: String, productPrice: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "Producto recomendado",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Text(text = productName, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = productPrice, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun ProductDetailsScreenPreview() {
    // Creamos un NavController simulado para que la pantalla funcione correctamente
    val navController = rememberNavController()
    // Llamamos a la función principal de la pantalla con el NavController de prueba

    ProductDetailsScreenBuyer(navController = navController)
}

