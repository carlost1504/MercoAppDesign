package com.example.mercoapp.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomePage(modifier: Modifier= Modifier, navController: NavController){

    Column {
        Text(text = "Hola entre")
    }
}