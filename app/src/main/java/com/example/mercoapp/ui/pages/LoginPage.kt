package com.example.mercoapp.ui.pages

import android.os.Handler
import android.os.Looper
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.R
import com.example.mercoapp.Routes
import com.example.mercoapp.domain.model.AuthState
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.PasswordTextField
import com.example.mercoapp.ui.theme.redMerco
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.viewModel.SharedUserViewModel
import com.example.mercoapp.viewModel.UserViewModel


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel = viewModel(),
    sharedUserViewModel: SharedUserViewModel
) {
    val authState by authViewModel.authState.observeAsState(AuthState.Idle)
    val userId by authViewModel.userId.observeAsState()
    val userType by authViewModel.userType.observeAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    // Redirigir según el tipo de usuario
    LaunchedEffect(authState, userType) {
        when (authState) {
            is AuthState.Success -> {
                userId?.let { id ->
                    if (userType == null) {
                        authViewModel.fetchUserType(id) // Determina si es buyer o seller
                    } else {
                        when (userType) {
                            "buyer" -> navController?.navigate(Routes.HomeBuyer)
                            "seller" -> {
                                sharedUserViewModel.loadSellerData(id) // Carga los datos del vendedor
                                navController?.navigate(Routes.HomeSeller) // Navega al subgrafo del vendedor
                            }
                            else -> navController?.navigate(Routes.TypeUser)
                        }
                    }
                }
            }
            is AuthState.Error -> {
                // Maneja el error aquí
            }
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(navController = navController, "Inicio de sesión", Routes.MercoInit)
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
            CustomTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField(value = password, onValueChange = { password = it }, label = "Contraseña")
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (authState) {
            is AuthState.Idle -> Spacer(modifier = Modifier.height(16.dp))
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text(
                text = (authState as AuthState.Error).message,
                color = Color.Red
            )
            else -> {}
        }

        ActionButton(
            text = "Entrar",
            onClick = { authViewModel.signin(email, password) },
            backgroundColor = Color(0xFFFF6D00)
        )

        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = { navController?.navigate(Routes.TypeUser) }) {
            Text(text = "Si no tienes cuenta, crear cuenta")
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}
















