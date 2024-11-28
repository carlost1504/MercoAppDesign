package com.example.mercoapp.ui.pages.seller

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import coil.compose.rememberImagePainter
import com.example.mercoapp.ui.theme.redMerco
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercoapp.Routes
import com.example.mercoapp.Routes.HomeSeller
import com.example.mercoapp.domain.model.AuthState
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.components.ProfilePhotoButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupPageSeller(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.observeAsState(AuthState.Idle)

    // Campos de entrada
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var selectedDocumentType by rememberSaveable { mutableStateOf("") }
    val documentTypes = listOf("Cédula de ciudadanía", "Tarjeta de identidad", "Tarjeta de extranjería", "Pasaporte")
    var document by rememberSaveable { mutableStateOf("") }
    var cell by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val passwordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()
    var profilePhotoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var nameStore by rememberSaveable { mutableStateOf("") }
    var storeAddress by rememberSaveable { mutableStateOf("") }
    var storePhotoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var storeDescription by rememberSaveable { mutableStateOf("") }
    var addressShops by rememberSaveable { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // Variable para manejar el mensaje de error

    // Lanzadores para seleccionar imágenes
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        profilePhotoUri = uri
    }

    val storePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        storePhotoUri = uri
    }

    // Manejo de estados de autenticación
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                val sellerId = authViewModel.userId.value ?: ""
                navController?.navigate("${Routes.HomeSeller}/$sellerId") {
                    popUpTo(Routes.SigupSeller) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                errorMessage = (authState as AuthState.Error).message
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crear usuario vendedor", style = MaterialTheme.typography.body2) },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Header(navController = navController,"Crear usuario vendedor", "typeUser") }

            item { Spacer(modifier = Modifier.height(30.dp)) }

            // Campos de entrada
            item { CustomTextField(value = name, onValueChange = { name = it }, label = "Nombre") }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item { CustomTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellido") }
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Dropdown de tipo de documento
            item {
                DropdownButton(
                    items = documentTypes,
                    selectedValue = selectedDocumentType,
                    onValueSelected = { selectedDocumentType = it }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = document, onValueChange = { document = it }, label = "Documento") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = cell, onValueChange = { cell = it }, label = "Celular") }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Contraseña
            item { PasswordTextField(value = password, onValueChange = { password = it }, label = "Contraseña") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                PasswordTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirma contraseña",
                    passwordsMatch = passwordsMatch
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Foto de perfil
            item { ProfilePhotoButton(launcher = launcher,  "Cargar imagen de perfil") }
            item {
                profilePhotoUri?.let { uri ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberImagePainter(data = uri),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(128.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Información de la tienda
            item { CustomTextField(value = nameStore, onValueChange = { nameStore = it }, label = "Nombre de la tienda") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = storeAddress, onValueChange = { storeAddress = it }, label = "Dirección de la tienda") }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Foto de la tienda
            item { ProfilePhotoButton(launcher = storePhotoLauncher,  "Cargar foto de la tienda") }
            item {
                storePhotoUri?.let { uri ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = rememberImagePainter(data = uri),
                        contentDescription = "Foto de la tienda",
                        modifier = Modifier.size(128.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = storeDescription, onValueChange = { storeDescription = it }, label = "Descripción de la tienda") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CustomTextField(value = addressShops, onValueChange = { addressShops = it }, label = "Dirección de otros locales") }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Botón de registro
            item {
                ActionButton(
                    text = "Crear cuenta",
                    onClick = {
                        if (name.isEmpty() || lastName.isEmpty() || selectedDocumentType.isEmpty() ||
                            document.isEmpty() || email.isEmpty() || password.isEmpty() || !passwordsMatch ||
                            nameStore.isEmpty() || storeAddress.isEmpty()
                        ) {
                            errorMessage = "Por favor complete todos los campos correctamente."
                            return@ActionButton
                        }

                        val seller = UserSeller(
                            id = "",
                            name = name,
                            lastName = lastName,
                            documentTypes = selectedDocumentType,
                            document = document,
                            email = email,
                            cell = cell,
                            profilePhotoUri = profilePhotoUri?.toString() ?: "",
                            password = password,
                            nameStore = nameStore,
                            storeAddress = storeAddress,
                            storePhotoUri = storePhotoUri?.toString() ?: "",
                            storeDescription = storeDescription,
                            addressShops = addressShops
                        )

                        authViewModel.signupSeller(seller, password)
                    },
                    backgroundColor = redMerco
                )
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}



