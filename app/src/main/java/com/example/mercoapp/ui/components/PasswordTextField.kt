package com.example.mercoapp.ui.components

// PasswordTextField.kt
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.Color
import androidx.compose.material.MaterialTheme

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordsMatch: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(0.8f),
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = if (passwordsMatch) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = if (passwordsMatch) "Passwords match" else "Passwords do not match",
                    tint = if (passwordsMatch) Color.Green else Color.Red
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface,
            textColor = MaterialTheme.colors.onBackground
        )
    )
}
