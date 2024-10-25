package com.example.mercoapp.ui.pages

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextFieldDisplay
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.theme.redMerco
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userViewModel: UserViewModel = viewModel()
) {
    // Observa el estado de usuario (comprador o vendedor)
    val userBuyer by userViewModel.userBuyer.observeAsState()
    val userSeller by userViewModel.userSeller.observeAsState()
    val userState by userViewModel.userState.observeAsState()


    // Inicia la carga de datos del usuario (asegúrate de llamar a getUser con el ID correcto)
    LaunchedEffect(Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            userViewModel.getUser(userId)
        } else {
            Log.e("UserProfileScreen", "Error: userId is null. User may not be authenticated.")
        }
    }




    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Encabezado
        item { Header(navController = navController, "Perfil de Usuario", "userProfile") }
        item { Spacer(modifier = Modifier.height(30.dp)) }

        // Indicador de carga según el estado de usuario
        when (userState) {
            1 -> {
                // Estado de carga
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
            2 -> {
                // Estado de error
                item {
                    Text(
                        text = "Error al cargar datos del usuario.",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            }
            3 -> {
                // Estado de éxito
                userBuyer?.let { user ->
                    item {
                        Text(text = "Usuario: ${user.name}")
                        CustomTextFieldDisplay(value = user.name ?: "Nombre no disponible", label = "Nombre")
                        CustomTextFieldDisplay(value = user.lastName ?: "Apellido no disponible", label = "Apellido")
                    }
                }
                userSeller?.let { user ->
                    item {
                        Text(text = "Vendedor: ${user.name}")
                        CustomTextFieldDisplay(value = user.name ?: "Nombre de Vendedor no disponible", label = "Nombre de Vendedor")
                        CustomTextFieldDisplay(value = user.id ?: "ID de Vendedor no disponible", label = "ID de Vendedor")
                    }
                }
            }
            else -> {
                // Sin datos disponibles
                item {
                    Text(
                        text = "No se encontraron datos del usuario.",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                }
            }
        }
    }
}








