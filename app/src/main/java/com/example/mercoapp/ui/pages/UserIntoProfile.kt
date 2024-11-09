package com.example.mercoapp.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.CustomTextFieldDisplay
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userViewModel: UserViewModel = viewModel()
) {
    // Observa los estados de usuario y la carga
    val userBuyer by userViewModel.userBuyer.observeAsState()
    val userSeller by userViewModel.userSeller.observeAsState()
    val userState by userViewModel.userState.observeAsState(0)

    // Inicia la carga de datos del usuario solo si es la primera vez que se compone
    LaunchedEffect(Unit) {
        userViewModel.loadCurrentUser()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Header(navController = navController,  "Perfil de Usuario",  "userProfile")
            Spacer(modifier = Modifier.height(30.dp))
        }

        // Control de estado de carga
        when (userState) {
            1 -> { // Estado de carga
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = "Cargando datos del usuario...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            2 -> { // Estado de error
                item {
                    Text(
                        text = "Error al cargar datos del usuario.",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            3 -> { // Estado de éxito, mostrando la información del usuario
                userBuyer?.let { user ->
                    item {
                        UserDetails(user = user)
                    }
                }

                userSeller?.let { user ->
                    item {
                        UserDetails(user = user)
                    }
                }
            }
            else -> { // Sin datos disponibles
                item {
                    Text(
                        text = "No se encontraron datos del usuario.",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun UserDetails(user: Any) {
    when (user) {
        is UserBuyer -> {
            // Renderiza la información específica de UserBuyer
            CustomTextFieldDisplay(value = user.name ?: "Nombre no disponible", label = "Nombre")
            CustomTextFieldDisplay(value = user.lastName ?: "Apellido no disponible", label = "Apellido")
            CustomTextFieldDisplay(value = user.typeDocument ?: "Tipo de Documento no disponible", label = "Tipo de Documento")
            CustomTextFieldDisplay(value = user.document ?: "Documento no disponible", label = "Documento")
            CustomTextFieldDisplay(value = user.email ?: "Correo electrónico no disponible", label = "Correo Electrónico")
            CustomTextFieldDisplay(value = user.cell ?: "Número de celular no disponible", label = "Celular")
        }
        is UserSeller -> {
            // Renderiza la información específica de UserSeller
            CustomTextFieldDisplay(value = user.name ?: "Nombre de Vendedor no disponible", label = "Nombre de Vendedor")
            CustomTextFieldDisplay(value = user.lastName ?: "Apellido no disponible", label = "Apellido")
            CustomTextFieldDisplay(value = user.documentTypes ?: "Tipo de Documento no disponible", label = "Tipo de Documento")
            CustomTextFieldDisplay(value = user.document ?: "Documento no disponible", label = "Documento")
            CustomTextFieldDisplay(value = user.email ?: "Correo electrónico no disponible", label = "Correo Electrónico")
            CustomTextFieldDisplay(value = user.cell ?: "Número de celular no disponible", label = "Celular")
            CustomTextFieldDisplay(value = user.storeAddress ?: "Dirección no disponible", label = "Dirección de la tienda")
        }
    }
}





