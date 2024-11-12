package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun UserProfileScreen(navController: NavController?) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", style = MaterialTheme.typography.titleMedium) },
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileHeader(
                imageUrl = "https://via.placeholder.com/150", // URL de ejemplo para la imagen del perfil
                name = "Laura",
                email = "Laura@mail.com"
            )
            Spacer(modifier = Modifier.height(32.dp))
            ProfileOptionItem(title = "Mis pedidos", subtitle = "Ya tienes 12 pedidos") {
                // Navegación a la pantalla de pedidos
            }
            ProfileOptionItem(title = "Mis reseñas", subtitle = "Reseñas de 4 artículos") {
                // Navegación a la pantalla de reseñas
            }
            ProfileOptionItem(title = "Ajustes", subtitle = "Notificaciones, contraseña") {
                // Navegación a la pantalla de ajustes
            }
            ProfileOptionItem(title = "Cerrar sesión") {
                // Acción de cerrar sesión
            }
        }
    }
}

@Composable
fun ProfileHeader(imageUrl: String, name: String, email: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(text = email, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
    }
}

@Composable
fun ProfileOptionItem(title: String, subtitle: String? = null, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                subtitle?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Abrir",
                tint = Color.Gray
            )
        }
        Divider(color = Color.LightGray)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun UserProfileScreenPreview() {
    // Simulamos el NavController para la previsualización
    val navController = rememberNavController()

    UserProfileScreen(navController = navController)
}
