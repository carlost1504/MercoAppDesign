package com.example.mercoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownButton(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto que mostrará el ítem seleccionado
        OutlinedTextField(
            value = selectedItem,
            onValueChange = { selectedItem = it },
            enabled = false,  // Deshabilitado para que no pueda ser editado
            readOnly = true,  // Solo lectura
            label = { Text("Tipo de Documento") }, // Usa el color del tema
            modifier = modifier
                .clickable { expanded = true }  // Al hacer clic, se abre el menú
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(0.9f),  // Ocupa el 80% del ancho disponible
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "type Document", tint = MaterialTheme.colors.primary) // Icono con color del tema
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,  // Borde enfocado con el color del tema
                unfocusedBorderColor = MaterialTheme.colors.onSurface, // Borde no enfocado
                textColor = MaterialTheme.colors.onBackground  // Color del texto
            )
        )

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },  // Se cierra al hacer clic fuera del menú
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(start = 20.dp, end = 20.dp) // Alinea el menú con el ancho del OutlinedTextField
        ) {
            // Itera sobre la lista de items
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = item  // Actualiza el ítem seleccionado
                        expanded = false  // Cierra el menú después de seleccionar
                    }
                ) {
                    Text(text = item, color = MaterialTheme.colors.onSurface)  // Muestra el ítem con el color del tema
                }
            }
        }
    }
}