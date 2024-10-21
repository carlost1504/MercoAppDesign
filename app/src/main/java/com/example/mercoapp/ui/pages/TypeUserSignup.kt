package com.example.mercoapp.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mercoapp.AuthViewModel
import com.example.mercoapp.R
import com.example.mercoapp.ui.theme.redMerco
import androidx.compose.material.Button
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.Header


@Composable
fun TypeUserSignup(
    modifier: Modifier = Modifier,
    navController: NavController?,
    authViewModel: AuthViewModel?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Agrega padding alrededor de la pantalla
        verticalArrangement = Arrangement.SpaceBetween, // Espacio entre elementos
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(navController = navController,"Tipo de cuente","login")

        Image(
            painter = painterResource(id = R.drawable.logo_merco_app), // Coloca aquí tu logo
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape) // Hace que la imagen sea circular
        )
        Spacer(modifier = Modifier.height(20.dp)) // Espaciador de 30.dp entre los botones

        ActionButton(
            text = "Vendedor",
            onClick = { navController?.navigate("signupSeller") },
            backgroundColor = redMerco
        )

        ActionButton(
            text = "Comprador",
            onClick = { navController?.navigate("signupBuyer") },
            backgroundColor = redMerco
        )

        Spacer(modifier = Modifier.height(30.dp)) // Espaciador de 30.dp entre los botones


    }
}

@Preview(showBackground = true)
@Composable
fun TypeUserSignupPreview() {
    TypeUserSignup(
        navController = null,
        authViewModel = null
    )
}


