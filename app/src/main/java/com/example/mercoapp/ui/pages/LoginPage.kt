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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mercoapp.viewModel.AuthViewModel
import com.example.mercoapp.R
import com.example.mercoapp.domain.model.AuthState
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
    userViewModel: UserViewModel = viewModel() // Conecta el UserViewModel para usar después del login
) {
    val authState by authViewModel.authState.observeAsState(AuthState.Idle) // Observa el estado de autenticación
    val userId by authViewModel.userId.observeAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    // Reaccionar a los estados de autenticación
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                userId?.let { id ->
                    userViewModel.getUser(id) // Carga los datos del usuario autenticado
                    navController?.navigate("infoUser") // Navega a la pantalla del perfil de usuario
                }
            }
            is AuthState.Error -> {
                // Manejo de error: Puedes agregar lógica adicional aquí si es necesario
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
        // Encabezado
        Header(
            navController = navController,
            "Inicio de sesión",
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
            is AuthState.Idle -> Spacer(modifier = Modifier.height(16.dp)) // Estado inicial
            is AuthState.Loading -> CircularProgressIndicator() // Estado: Cargando
            is AuthState.Error -> Text(
                text = (authState as AuthState.Error).message, // Mostrar el mensaje de error
                color = Color.Red
            )
            is AuthState.Success -> Spacer(modifier = Modifier.height(16.dp)) // Nada aquí, ya se maneja en LaunchedEffect
        }

        ActionButton(
            text = "Entrar",
            onClick = {
                authViewModel.signin(email, password) // Llama a la función signin del ViewModel
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

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LoginPagePreview() {
    // Simulamos un NavController
    val navController = rememberNavController()

    // Simulamos un AuthViewModel con estados de prueba
    val fakeAuthViewModel = object : AuthViewModel() {
        private val _authState = MutableLiveData<AuthState>(AuthState.Idle)
        private val _userId = MutableLiveData<String?>(null)

        override val authState: LiveData<AuthState> = _authState
        override val userId: LiveData<String?> = _userId

        override fun signin(email: String, password: String) {
            _authState.value = AuthState.Loading
            // Simula éxito después de un segundo
            Handler(Looper.getMainLooper()).postDelayed({
                _userId.value = "mock_user_id"
                _authState.value = AuthState.Success
            }, 1000)
        }
    }

    // Simulamos un UserViewModel (sin lógica real para este ejemplo)
    val fakeUserViewModel = object : UserViewModel() {}

    // Renderizamos la LoginPage con los valores simulados
    LoginPage(
        navController = navController,
        authViewModel = fakeAuthViewModel,
        userViewModel = fakeUserViewModel
    )
}














