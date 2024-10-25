package com.example.mercoapp.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.R
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.theme.redMerco
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.viewModel.UserViewModel


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()  // Conecta el UserViewModel para usar después del login
) {
    val authState by authViewModel.authState.observeAsState(0)
    val userId by authViewModel.userId.observeAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Header(
            navController = navController,
            "Inicio de sesion",
            "mercoInit"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_merco_app),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de correo electrónico
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña"
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar estado de autenticación
        when (authState) {
            0 -> Spacer(modifier = Modifier.height(16.dp)) // Estado inicial
            1 -> CircularProgressIndicator() // Estado: Cargando
            2 -> Text(text = "Hubo un error al iniciar sesión, por favor intenta de nuevo", color = Color.Red) // Estado: Error
            3 -> {
                // Navega a la pantalla del perfil de usuario con el ID del usuario autenticado
                userId?.let { id ->
                    userViewModel.getUser(id) // Llama a getUser en UserViewModel para cargar la información
                    navController?.navigate("infoUser") // Navegar a la pantalla de perfil
                }
            }
        }

        ActionButton(
            text = "Entrar",
            onClick = {
                authViewModel.signin(email, password) // Inicia sesión
            },
            backgroundColor = redMerco
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para crear cuenta
        TextButton(onClick = {
            navController?.navigate("typeUser")
        }) {
            Text(text = "Si no tienes cuenta, crear cuenta")
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}





@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    // Crear un NavController simulado para la vista previa
    val navController = rememberNavController()

    // Crear una versión simulada de AuthViewModel con estados predefinidos
    val fakeAuthViewModel = object : AuthViewModel() {
        // Para la vista previa, devolveremos un estado predeterminado
        override val authState = MutableLiveData(0)
    }

    // Llamamos a LoginPage pasando los valores simulados
    LoginPage(
        navController = navController,
        authViewModel = fakeAuthViewModel
    )
}










