package com.example.mercoapp.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Categorías", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de retroceso */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AllCategoriesButton()
            Text(
                text = "Elige categoría",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CategoryList()
        }
    }
}

@Composable
fun AllCategoriesButton() {
    Button(
        onClick = { /* Acción para ver todas las categorías */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Todas las Categorías",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CategoryList() {
    val categories = listOf(
        "Comida Rápida",
        "Frutas y Verduras",
        "Lácteos y Derivados",
        "Postres y Repostería",
        "Carnes y Embutidos",
        "Abarrotes y Productos Secos",
        "Especias y Condimentos",
        "Bebidas",
        "Comida para Niños",
        "Comida para Mascotas",
        "Snacks y Dulces"
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories.size) { index ->
            CategoryItem(categories[index])
        }
    }
}

@Composable
fun CategoryItem(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    )
}



@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CategoriesScreenPreview() {
    MaterialTheme {
        CategoriesScreen()
    }
}
