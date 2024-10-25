package com.example.mercoapp.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.viewModel.UserViewModel

@Composable
fun UserLoadScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    val userId by authViewModel.userId.observeAsState()
    val loadState by userViewModel.userState.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pulsa el botón para cargar tu perfil")

        Spacer(modifier = Modifier.height(20.dp))



        Button(
            onClick = {
                userId?.let {
                    userViewModel.getUser(it) // Inicia la carga de datos con el userId
                } ?: Log.d("UserLoadScreen", "userId es null, no se puede cargar el perfil")
            },
            enabled = userId != null // Deshabilita el botón si userId es null
        ) {
            Text(text = "Cargar Perfil")
        }

        // Maneja estados de carga, éxito o error
        when (loadState) {
            1 -> CircularProgressIndicator() // Cargando
            2 -> Text(text = "Error al cargar datos. Intenta de nuevo.")
            3 -> LaunchedEffect(loadState) {
                navController.navigate("infoUser")
            }
        }
    }
}


