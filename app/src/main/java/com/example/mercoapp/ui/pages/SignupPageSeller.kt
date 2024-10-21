package com.example.mercoapp.ui.pages

import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
import coil.compose.rememberImagePainter
import com.example.mercoapp.ui.theme.redMerco
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercoapp.domain.model.UserBuyer
import com.example.mercoapp.domain.model.UserSeller
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.components.ProfilePhotoButton
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun SignupPageSeller(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel = viewModel()
) {
    val authState by authViewModel.authState.observeAsState(0) // Observamos el estado de autenticación

    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    val selectedDocumentType by rememberSaveable { mutableStateOf("") }  // Variable para almacenar el tipo de documento seleccionado
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

    // Lanzador para abrir el selector de imágenes
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profilePhotoUri = uri
    }

    // Lanzador para cargar foto de la tienda
    val storePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        storePhotoUri = uri
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Header(navController = navController, "Crear usuario vendedor", "typeUser") }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item { CustomTextField(value = name, onValueChange = { name = it }, label = "Nombre") }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item { CustomTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellido") }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        item { DropdownButton(items = documentTypes) }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { CustomTextField(value = document, onValueChange = { document = it }, label = "Documento") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { CustomTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item { CustomTextField(value = cell, onValueChange = { cell = it }, label = "Celular") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña"
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            PasswordTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirma contraseña",
                passwordsMatch = passwordsMatch
            )
        }

        // Manejar el estado de autenticación
        when (authState) {
            1 -> item { CircularProgressIndicator() } // Estado: Cargando
            2 -> item {
                Text(text = "Hubo un error al registrar la cuenta, por favor intenta de nuevo", color = Color.Red)
            } // Estado: Error
            3 -> item {
                // Estado: Éxito, navegar a la pantalla de inicio
                navController?.navigate("home")
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Foto de perfil
        item { ProfilePhotoButton(launcher = launcher,"Cargar imagen de perfil") }

        item {
            profilePhotoUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberImagePainter(data = uri),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(128.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Nuevos campos relacionados con la tienda

        // Nombre de la tienda
        item { CustomTextField(value = nameStore, onValueChange = { nameStore = it }, label = "Nombre de la tienda") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Dirección de la tienda
        item { CustomTextField(value = storeAddress, onValueChange = { storeAddress = it }, label = "Dirección de la tienda") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Botón para cargar foto de la tienda
        item { ProfilePhotoButton(launcher = storePhotoLauncher, "Cargar foto de la tienda") }

        item {
            storePhotoUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = rememberImagePainter(data = uri),
                    contentDescription = "Store Image",
                    modifier = Modifier.size(128.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Descripción de la tienda
        item { CustomTextField(value = storeDescription, onValueChange = { storeDescription = it }, label = "Descripción de la tienda") }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Dirección de otros locales
        item { CustomTextField(value = addressShops, onValueChange = { addressShops = it }, label = "Dirección de otros locales") }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        // Botón para crear cuenta
        item {
            ActionButton(
                text = "Crear cuenta",
                onClick = {
                    if (passwordsMatch) {
                        // Crear una instancia de UserBuyer con los datos del formulario
                        val seller = UserSeller(
                            id = "",
                            name = name,
                            lastName = lastName,
                            documentTypes = selectedDocumentType,  // Aquí se usa el tipo de documento seleccionado
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

                        // Llamar a la función para registrar comprador
                        authViewModel.signupSeller(seller, password)
                        navController?.navigate("home")

                        FirebaseFirestore.getInstance().collection("users").document(seller.email).set(seller)
                        println("datos enviado"+ seller.id+ " "+seller.name)
                    }
                },
                backgroundColor = redMerco
            )
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun SigniupPagePreview() {
    // Aquí no necesitas pasar navController ni authViewModel para la vista previa
    SignupPageBuyer(navController = null, authViewModel = null)
}
 */