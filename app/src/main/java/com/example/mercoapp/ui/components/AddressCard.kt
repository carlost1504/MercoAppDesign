package com.example.mercoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddressCard(title: String, address: String, changeable: Boolean = false) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = address, style = MaterialTheme.typography.bodyMedium)
            if (changeable) {
                Text(
                    text = "Cambiar",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { /* Acción para cambiar dirección */ }
                )
            }
        }
    }
}