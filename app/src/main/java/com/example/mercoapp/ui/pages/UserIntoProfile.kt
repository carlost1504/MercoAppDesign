package com.example.mercoapp.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.BottomNavigationBarr
import com.example.mercoapp.ui.components.CustomTextFieldDisplay
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.viewModel.UserViewModel



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

    Scaffold(

        topBar={
            Header(navController = navController,  "Perfil de Usuario",  "userProfile")
        },
        bottomBar = {
            BottomNavigationBarr(navController, "Perfil") // Cambia "Perfil" según el nombre de la pantalla actual

        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {


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
                            UserDetails(user = user,modifier)
                        }
                    }

                    userSeller?.let { user ->
                        item {
                            UserDetails(user = user, modifier)
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
}

@Composable
fun UserDetails(user: Any,modifier: Modifier = Modifier) {
    when (user) {
        is UserBuyer -> {
            // Renderiza la información específica de UserBuyer
            CustomTextFieldDisplay(
                value = user.name ?: "Nombre no disponible",
                label = "Nombre",

            )
            CustomTextFieldDisplay(
                value = user.lastName ?: "Apellido no disponible",
                label = "Apellido",
            )
            CustomTextFieldDisplay(
                value = user.typeDocument ?: "Tipo de Documento no disponible",
                label = "Tipo de Documento",
            )
            CustomTextFieldDisplay(
                value = user.document ?: "Documento no disponible",
                label = "Documento",
            )
            CustomTextFieldDisplay(
                value = user.email ?: "Correo electrónico no disponible",
                label = "Correo Electrónico",
            )
            CustomTextFieldDisplay(
                value = user.cell ?: "Número de celular no disponible",
                label = "Celular",
            )
        }
        is UserSeller -> {
            // Renderiza la información específica de UserSeller
            CustomTextFieldDisplay(
                value = user.name ?: "Nombre de Vendedor no disponible",
                label = "Nombre de Vendedor",
            )
            CustomTextFieldDisplay(
                value = user.lastName ?: "Apellido no disponible",
                label = "Apellido",
            )
            CustomTextFieldDisplay(
                value = user.documentTypes ?: "Tipo de Documento no disponible",
                label = "Tipo de Documento",
            )
            CustomTextFieldDisplay(
                value = user.document ?: "Documento no disponible",
                label = "Documento",
            )
            CustomTextFieldDisplay(
                value = user.email ?: "Correo electrónico no disponible",
                label = "Correo Electrónico",
            )
            CustomTextFieldDisplay(
                value = user.cell ?: "Número de celular no disponible",
                label = "Celular",
            )
            CustomTextFieldDisplay(
                value = user.storeAddress ?: "Dirección no disponible",
                label = "Dirección de la tienda",
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun UserProfileScreenPreview() {
    // Creamos un NavController de prueba
    val navController = rememberNavController()

    // Creamos un ViewModel de prueba
    val userViewModel = remember {
        object : UserViewModel() {
            // Sobreescribimos los datos para el Preview
            init {
                _userBuyer.value = UserBuyer(
                    name = "Laura",
                    lastName = "Pérez",
                    typeDocument = "CC",
                    document = "123456789",
                    email = "laura@example.com",
                    cell = "3201234567",
                    profilePhotoUri = "" // Uri simulada o puedes usar una imagen de recurso
                )
                _userSeller.value = null // Dejamos a null ya que estamos simulando solo un tipo de usuario
                userState.value = 3 // Estado de éxito para mostrar datos
            }
        }
    }

    // Llamamos a la función UserProfileScreen con el NavController y ViewModel de prueba
    UserProfileScreen(
        navController = navController,
        userViewModel = userViewModel
    )
}





