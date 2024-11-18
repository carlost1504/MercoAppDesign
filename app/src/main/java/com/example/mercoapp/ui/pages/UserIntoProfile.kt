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
fun UserBuyerProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userViewModel: UserViewModel = viewModel()
) {
    // Observa los datos del usuario y el estado
    val user by userViewModel.user.observeAsState()
    val userState by userViewModel.userState.observeAsState(0)

    // Inicia la carga de datos del usuario solo si es la primera vez que se compone
    LaunchedEffect(Unit) {
        userViewModel.loadCurrentUser()
    }

    Scaffold(
        topBar = {
            Header(navController = navController, "Perfil de Comprador", "userProfile")
        },
        bottomBar = {
            BottomNavigationBarr(navController, "Perfil")
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
                3 -> { // Estado de éxito
                    if (user is UserBuyer) {
                        (user as? UserBuyer)?.let { buyer ->
                            item {
                                BuyerDetails(buyer = buyer, modifier)
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = "No se encontraron datos del comprador.",
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }
                else -> { // Estado inicial o desconocido
                    item {
                        Text(
                            text = "No se encontraron datos del usuario.",
                            color = Color.Gray,
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
fun BuyerDetails(buyer: UserBuyer, modifier: Modifier = Modifier) {
    // Renderiza la información específica de UserBuyer
    Column(modifier = modifier.fillMaxWidth()) {
        CustomTextFieldDisplay(
            value = buyer.name ?: "Nombre no disponible",
            label = "Nombre",
        )
        CustomTextFieldDisplay(
            value = buyer.lastName ?: "Apellido no disponible",
            label = "Apellido",
        )
        CustomTextFieldDisplay(
            value = buyer.typeDocument ?: "Tipo de Documento no disponible",
            label = "Tipo de Documento",
        )
        CustomTextFieldDisplay(
            value = buyer.document ?: "Documento no disponible",
            label = "Documento",
        )
        CustomTextFieldDisplay(
            value = buyer.email ?: "Correo electrónico no disponible",
            label = "Correo Electrónico",
        )
        CustomTextFieldDisplay(
            value = buyer.cell ?: "Número de celular no disponible",
            label = "Celular",
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun UserBuyerProfileScreenPreview() {
    val navController = rememberNavController()

    val userViewModel = remember {
        object : UserViewModel() {
            init {
                _userBuyer.postValue(
                    UserBuyer(
                        name = "Laura",
                        lastName = "Pérez",
                        typeDocument = "CC",
                        document = "123456789",
                        email = "laura@example.com",
                        cell = "3201234567",
                        profilePhotoUri = ""
                    )
                )
                _userState.postValue(3)
            }
        }
    }

    UserBuyerProfileScreen(
        navController = navController,
        userViewModel = userViewModel
    )
}
