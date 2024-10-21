package com.example.mercoapp.ui.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mercoapp.AuthViewModel
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.mercoapp.ui.theme.redMerco
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.components.ProfilePhotoButton


@Composable
fun SignupPageBuyer(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel?
) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
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
        item { Header(navController = navController, "Crear usuario comprador", "typeUser") }

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
                onClick = { navController?.navigate("home") },
                backgroundColor = redMerco
            )
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}



@Preview(showBackground = true)
@Composable
fun SigniupPagePreview() {
    // Aquí no necesitas pasar navController ni authViewModel para la vista previa
    SignupPageBuyer(navController = null, authViewModel = null)
}