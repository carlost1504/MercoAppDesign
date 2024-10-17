package com.example.mercoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mercoapp.pages.HomePage
import com.example.mercoapp.pages.LoginPage
import com.example.mercoapp.pages.SignupPageBuyer
import com.example.mercoapp.pages.SignupPageSeller
import com.example.mercoapp.pages.TypeUserSignup
import androidx.compose.ui.tooling.preview.Preview as Preview1




@Composable
fun MyAppNavegation(modifier: Modifier=Modifier, authViewModel: AuthViewModel){
    val navController= rememberNavController()

    NavHost(navController= navController, startDestination = "login", builder = {
        composable("login"){
            LoginPage(modifier,navController,authViewModel)
        }
        composable("typeUser"){
            TypeUserSignup(modifier,navController,authViewModel)
        }
        composable("signupBuyer"){
            SignupPageBuyer(modifier,navController,authViewModel)//
        }
        composable("signupSeller"){
            SignupPageSeller(modifier,navController,authViewModel)//
        }

        composable("home"){
            HomePage(modifier,navController,authViewModel)
        }
    })
}

