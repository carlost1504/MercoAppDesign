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
import com.example.mercoapp.ui.pages.HomePage
import com.example.mercoapp.ui.pages.LoginPage
import com.example.mercoapp.ui.pages.MercoInit
import com.example.mercoapp.ui.pages.SignupPageBuyer
import com.example.mercoapp.ui.pages.SignupPageSeller
import com.example.mercoapp.ui.pages.TypeUserSignup
import androidx.compose.ui.tooling.preview.Preview as Preview1




@Composable
fun MyAppNavegation(modifier: Modifier=Modifier, authViewModel: AuthViewModel){
    val navController= rememberNavController()

    NavHost(navController= navController, startDestination = "mercoInit", builder = {

        composable("mercoInit"){
            MercoInit(modifier,navController,authViewModel)
        }
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

