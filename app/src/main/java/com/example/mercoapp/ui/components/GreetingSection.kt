package com.example.mercoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mercoapp.viewModel.SharedUserViewModel

@Composable
fun GreetingSection(sharedUserViewModel: SharedUserViewModel) {
    val buyer by sharedUserViewModel.buyer.observeAsState()

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "Hola, ${buyer?.name ?: "Usuario"}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "¿Qué estás buscando hoy?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}