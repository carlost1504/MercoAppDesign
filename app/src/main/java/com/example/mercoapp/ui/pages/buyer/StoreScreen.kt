package com.example.mercoapp.ui.pages.buyer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.R
import com.example.mercoapp.ui.components.BottomNavigationBarr
import com.example.mercoapp.viewModel.UserViewModel


@Composable
fun StoreScreen(modifier: Modifier= Modifier,
                navController: NavController?,
                userViewModel: UserViewModel = viewModel()
) {

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(

    navController: NavController?,

) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Tienda", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de retroceso */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBarr(navController,"Tienda")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            GreetingSection()
            SearchBar()
            ProductGrid()
        }
    }
}

@Composable
fun GreetingSection() {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = "Hola, Laura", style = MaterialTheme.typography.bodyMedium)
        Text(
            text = "¿Qué estás buscando hoy?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        TextField(
            value = "",
            onValueChange = { /* Acción de búsqueda */ },
            placeholder = { Text("Buscar") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF0F0F0))
        )
        IconButton(onClick = { /* Acción de filtros */ }) {
            Icon(Icons.Outlined.Menu, contentDescription = "Filtros")
        }
    }
}

@Composable
fun ProductGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) { // Cambia este valor o usa una lista dinámica
            ProductItem()
        }
    }
}

@Composable
fun ProductItem() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplaza con el recurso adecuado
                contentDescription = "Cake Red Velvet",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Icon(
                        painter = painterResource(id = R.drawable.estrella_gris_app), // Reemplaza con el ícono de estrella
                        contentDescription = "Rating Star",
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
            Text(
                text = "Cake Red Velvet",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "$20.000",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}



@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun StoreScreenPreview() {
    StoreScreen(navController = rememberNavController())
}




