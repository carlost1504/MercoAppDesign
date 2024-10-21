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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import com.example.mercoapp.AuthViewModel
import com.example.mercoapp.R
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.theme.redMerco
import com.example.mercoapp.ui.components.Header


@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController?, authViewModel: AuthViewModel?) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), // Padding lateral
        verticalArrangement = Arrangement.SpaceBetween, // Distribución de espacio entre los elementos
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
            painter = painterResource(id = R.drawable.logo_merco_app), // Coloca aquí tu logo
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape) // Hace que la imagen sea circular
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

        // Botón de ingreso
        ActionButton(
            text = "Entrar",
            onClick = {
                // Lógica de inicio de sesión
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
    // Aquí no necesitas pasar navController ni authViewModel para la vista previa
    LoginPage(navController = null, authViewModel = null)
}










