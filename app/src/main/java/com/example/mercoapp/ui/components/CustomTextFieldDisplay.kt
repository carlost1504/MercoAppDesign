package com.example.mercoapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.MaterialTheme

@Composable
fun CustomTextFieldDisplay(value: String, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},  // No permite cambios
        label = { Text(text = label) },
        enabled = false,  // Desactiva la edición
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = MaterialTheme.colors.onBackground,
            disabledBorderColor = MaterialTheme.colors.onSurface,
            disabledLabelColor = MaterialTheme.colors.onSurface
        )
    )
}
