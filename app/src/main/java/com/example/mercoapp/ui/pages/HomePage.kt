package com.example.mercoapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mercoapp.R

@Composable
fun HomePage(modifier: Modifier= Modifier, navController: NavController){

    Column {
        Text(text = "Hola entre")
    }
}


@Composable
fun HomeScreen() {
    Scaffold(
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
            Text(
                text = "Bienvenida Laura a Merco",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TabRow()
            DiscountCard()
            ProductList()
        }
    }
}

@Composable
fun TabRow() {
    TabRow(selectedTabIndex = 0) {
        Tab(selected = true, onClick = { /*TODO*/ }, text = { Text("Panadería") })
        Tab(selected = false, onClick = { /*TODO*/ }, text = { Text("Verduras") })
        Tab(selected = false, onClick = { /*TODO*/ }, text = { Text("Más categorías") })
    }
}

@Composable
fun DiscountCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Descuentos nuevos", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Hasta 50% en tiendas nuevas", color = Color.White)
        }
    }
}

@Composable
fun ProductList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { ProductItem("Pasteles", R.drawable.ic_launcher_background) }
        item { ProductItem("Panes", R.drawable.ic_launcher_background) }
        item { ProductItem("Fritos", R.drawable.ic_launcher_background) }
        item { ProductItem("Otros", R.drawable.ic_launcher_background) }
    }
}

@Composable
fun ProductItem(title: String, imageRes: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Text(text = title, modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") }, label = { Text("Inicio") }, selected = true, onClick = { /*TODO*/ })
        NavigationBarItem(icon = { Icon(Icons.Filled.Menu, contentDescription = "Tienda") }, label = { Text("Tienda") }, selected = false, onClick = { /*TODO*/ })
        NavigationBarItem(icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito") }, label = { Text("Carrito") }, selected = false, onClick = { /*TODO*/ })
        NavigationBarItem(icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") }, label = { Text("Favoritos") }, selected = false, onClick = { /*TODO*/ })
        NavigationBarItem(icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") }, label = { Text("Perfil") }, selected = false, onClick = { /*TODO*/ })
    }
}



@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

