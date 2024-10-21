package com.example.mercoapp.ui.pages

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.AuthViewModel
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.components.ProfilePhotoButton
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.theme.redMerco

@Composable
fun SignupPageSeller(
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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profilePhotoUri = uri
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Header(navController = navController,"Crear usuario vendedor","typeUser") }
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
        item { ProfilePhotoButton(launcher = launcher,"Cargar imagen de perfil") }
        item {
            profilePhotoUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))
                Image(painter = rememberImagePainter(data = uri), contentDescription = "Profile Image", modifier = Modifier.size(128.dp))
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ActionButton(text = "Crear cuenta", onClick = { navController?.navigate("home") }, backgroundColor = redMerco)
        }
        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}


@Preview(showBackground = true)
@Composable
fun SigniupSellerPagePreview() {
    SignupPageSeller(navController = null, authViewModel = null)
}
