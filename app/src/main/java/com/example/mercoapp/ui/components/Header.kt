package com.example.mercoapp.ui.components

// Header.kt
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Header(navController: NavController?,textHeader:String,rute:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        IconButton(
            onClick = { navController?.navigate(rute) },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-8).dp)
        ) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back button")
        }

        Text(
            text = textHeader,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
