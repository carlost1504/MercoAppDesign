package com.example.mercoapp.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mercoapp.R
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.theme.redMerco
import com.example.mercoapp.viewModel.AuthViewModel

@Composable
fun MercoInit(modifier: Modifier = Modifier,navController: NavController?,authViewModel: AuthViewModel?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_merco_app), // Coloca aquí tu logo
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape) // Hace que la imagen sea circular
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Eslogan
        Text(
            text = "Dale una segunda oportunidad a la comida con Merco.\nEvita los desperdicios.",
            style = TextStyle(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(30.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        ActionButton(
            text = "Ingresar",
            onClick = { navController?.navigate("login") },
            backgroundColor = redMerco
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MercoInitPreview() {
    // Aquí no necesitas pasar navController ni authViewModel para la vista previa
    MercoInit(navController = null, authViewModel = null)
}
