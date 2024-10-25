package com.example.mercoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mercoapp.ui.pages.HomePage
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.SignupPageSeller
import com.example.mercoapp.ui.pages.SignupPageBuyer
import com.example.mercoapp.ui.pages.TypeUserSignup
import com.example.mercoapp.ui.pages.UserLoadScreen
import com.example.mercoapp.ui.pages.UserProfileScreen
import com.example.mercoapp.viewModel.AuthViewModel


@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mercoInit") {

        composable("mercoInit") {
            MercoInit(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("login") {
            LoginPage(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("typeUser") {
            TypeUserSignup(
                modifier = modifier,
                navController = navController
            )
        }

        composable("signupBuyer") {
            SignupPageBuyer(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("signupSeller") {
            SignupPageSeller(
                modifier = modifier,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("userLoad") {
            UserLoadScreen(
                navController = navController,
                authViewModel = authViewModel,
                userViewModel = viewModel()  // Pasa userViewModel para manejo de datos
            )
        }

        composable("infoUser") {
            UserProfileScreen(
                modifier = modifier,
                navController = navController,
                userViewModel = viewModel()  // Pasa userViewModel para datos de perfil
            )
        }

        composable("home") {
            HomePage(
                modifier = modifier,
                navController = navController
            )
        }
    }
}

