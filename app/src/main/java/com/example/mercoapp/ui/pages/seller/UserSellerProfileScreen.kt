package com.example.mercoapp.ui.pages.seller

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mercoapp.viewModel.AuthViewModel
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import coil.compose.rememberImagePainter
import com.example.mercoapp.ui.theme.redMerco
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.BottomNavigationBarr
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.CustomTextFieldDisplay
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.components.ProfilePhotoButton
import com.example.mercoapp.viewModel.UserViewModel

@Composable
fun UserSellerProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    userViewModel: UserViewModel = viewModel()
) {
    // Observa los datos del usuario vendedor y el estado
    val user by userViewModel.user.observeAsState()
    val userState by userViewModel.userState.observeAsState(0)

    Scaffold(
        topBar = {
            Header(navController = navController, "Perfil del Vendedor", "sellerProfile")
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
            when {
                userState == 1 -> { // Estado de carga
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
                userState == 2 -> { // Estado de error
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
                user is UserSeller -> { // Estado de éxito con datos del vendedor
                    (user as? UserSeller)?.let { seller ->
                        item {
                            SellerDetails(seller = seller, modifier)
                        }
                    }
                }
                else -> { // Estado inicial o sin datos
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
fun SellerDetails(seller: UserSeller, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Información básica del vendedor
        CustomTextFieldDisplay(
            value = seller.name.ifEmpty { "Nombre no disponible" },
            label = "Nombre",
        )
        CustomTextFieldDisplay(
            value = seller.lastName.ifEmpty { "Apellido no disponible" },
            label = "Apellido",
        )
        CustomTextFieldDisplay(
            value = seller.documentTypes.ifEmpty { "Tipo de Documento no disponible" },
            label = "Tipo de Documento",
        )
        CustomTextFieldDisplay(
            value = seller.document.ifEmpty { "Documento no disponible" },
            label = "Documento",
        )
        CustomTextFieldDisplay(
            value = seller.email.ifEmpty { "Correo electrónico no disponible" },
            label = "Correo Electrónico",
        )
        CustomTextFieldDisplay(
            value = seller.cell.ifEmpty { "Número de celular no disponible" },
            label = "Celular",
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Información de la tienda
        Text(
            text = "Información de la Tienda",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        CustomTextFieldDisplay(
            value = seller.nameStore.ifEmpty { "Nombre de la tienda no disponible" },
            label = "Nombre de la Tienda",
        )
        CustomTextFieldDisplay(
            value = seller.storeAddress.ifEmpty { "Dirección de la tienda no disponible" },
            label = "Dirección de la Tienda",
        )
        CustomTextFieldDisplay(
            value = seller.storeDescription.ifEmpty { "Descripción de la tienda no disponible" },
            label = "Descripción de la Tienda",
        )

    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun SellerProfileScreenPreview() {
    // Creamos un NavController de prueba
    val navController = rememberNavController()

    // Simulamos un objeto UserSeller para la vista previa
    val mockSeller = UserSeller(
        id = "seller12345",
        name = "Carlos",
        lastName = "Ramírez",
        documentTypes = "CC",
        document = "987654321",
        email = "carlos@tienda.com",
        cell = "3209876543",
        profilePhotoUri = "https://via.placeholder.com/150",
        password = "password123",
        nameStore = "Tienda de Carlos",
        storeAddress = "Calle 123 #45-67, Bogotá",
        storePhotoUri = "https://via.placeholder.com/150",
        storeDescription = "La mejor tienda de tecnología en la ciudad.",
        addressShops = "Sucursal en Centro Comercial Andino",
        productIds = mutableListOf("prod1", "prod2", "prod3")
    )

    // Llamamos a la función SellerDetails directamente para simular la vista
    SellerDetails(seller = mockSeller)
}


