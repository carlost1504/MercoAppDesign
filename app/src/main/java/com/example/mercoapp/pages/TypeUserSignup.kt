package com.example.mercoapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mercoapp.AuthViewModel


@Composable
fun TypeUserSignup(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){



    Column(
        modifier= modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally

    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                navController.navigate("login")
            }) {
                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back button")
            }
            Text(text = "Tipo de usuario a crear")
            Spacer(modifier = Modifier
                .width(10.dp)
                .height(10.dp))
        }
        Text(text = "Tipo de usuario a crear", fontSize = 32.sp)
        Spacer(modifier= Modifier.height((16.dp)))

        Button(onClick = {
            navController.navigate("signupBuyer")
        }) {
            Text(text = "Comprador")
        }

        Spacer(modifier= Modifier.height((16.dp)))
        Button(onClick = {
            navController.navigate("signupSeller")
        }) {
            Text(text = "Vendedor")
        }



    }
}