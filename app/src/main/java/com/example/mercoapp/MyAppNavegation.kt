package com.example.mercoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mercoapp.ui.pages.HomePage
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.SignupPageSeller
import com.example.mercoapp.ui.pages.SignupPageBuyer
import com.example.mercoapp.ui.pages.TypeUserSignup




@Composable
fun MyAppNavegation(modifier: Modifier=Modifier, authViewModel: AuthViewModel){
    val navController= rememberNavController()

    NavHost(navController= navController, startDestination = "mercoInit", builder = {

        composable("mercoInit"){
            MercoInit(modifier,navController,authViewModel)
        }
        composable("login"){
            LoginPage(modifier,navController)
        }
        composable("typeUser"){
            TypeUserSignup(modifier,navController)
        }
        composable("signupBuyer"){
            SignupPageBuyer(modifier,navController)
        }
        composable("signupSeller"){
            SignupPageSeller(modifier,navController)//
        }
        composable("home"){
            HomePage(modifier,navController)
        }
    })
}

