package com.example.mercoapp.ui.pages.buyer


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.Routes
import com.example.mercoapp.ui.components.BottomNavigationBarrBuyer
import com.example.mercoapp.viewModel.SharedUserViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreenBuyer(
    navController: NavController?,
    sharedUserViewModel: SharedUserViewModel
) {
    // Observa los datos del comprador desde el ViewModel
    val buyer by sharedUserViewModel.buyer.observeAsState()
    val isLoading by sharedUserViewModel.isLoading.observeAsState(false)

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
            BottomNavigationBarrBuyer(
                navController = navController,
                currentScreen = "Perfil",
                sharedUserViewModel = sharedUserViewModel
            )
        }
    ) { padding ->
        if (isLoading) {
            // Muestra un indicador de carga si está cargando los datos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                    imageUrl = buyer?.profilePhotoUri ?: "https://via.placeholder.com/150",
                    name = "${buyer?.name ?: "Usuario"} ${buyer?.lastName ?: ""}",
                    email = buyer?.email ?: "Sin correo"
                )
                Spacer(modifier = Modifier.height(32.dp))
                ProfileOptionItem(
                    title = "Mis pedidos",
                    subtitle = "Historial de tus pedidos"
                ) {
                    // Navegación a la pantalla de historial de pedidos
                    navController?.navigate(Routes.OrderHistoryBuyer)
                }
                ProfileOptionItem(
                    title = "Mis reseñas",
                    subtitle = "Tus opiniones y valoraciones"
                ) {
                    // Navegación a la pantalla de reseñas
                }
                ProfileOptionItem(
                    title = "Ajustes",
                    subtitle = "Notificaciones, contraseña"
                ) {
                    // Navegación a la pantalla de ajustes
                }
                ProfileOptionItem(
                    title = "Cerrar sesión"
                ) {
                    // Acción de cerrar sesión
                    sharedUserViewModel.clearBuyerData()
                    navController?.navigate(Routes.Login) // Redirige a la pantalla de inicio de sesión
                }
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
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = email,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Abrir",
                tint = Color.Gray
            )
        }
        Divider(color = Color.LightGray)
    }
}
