package com.example.mercoapp.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mercoapp.AuthViewModel
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.unit.dp


//, navController: NavController, authViewModel: AuthViewModel
@Composable
fun SignupPageBuyer(modifier: Modifier= Modifier, navController: NavController, authViewModel: AuthViewModel){

    var name by remember { mutableStateOf("") }

    var last_Name by remember { mutableStateOf("") }

    //var tipo_documento by remember { mutableStateOf("") } se crea en una funcion aparte
    val documentTypes = listOf("Cédula de ciudadanía", "Tarjeta de identidad", "Tarjeta de extranjería","Pasaporte") // Your dropdown items example

    var document by  remember { mutableStateOf("") }

    var cell by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var profile_photo by remember { mutableStateOf("") }





    LazyColumn (
        modifier= modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally

    ){

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.navigate("typeUser")
                }) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back button")
                }
                Text(text = "Crear Cuenta")
                Spacer(modifier = Modifier
                    .width(10.dp)
                    .height(10.dp))
            }
        }

        item {
            Spacer(modifier=Modifier.height((30.dp)))
        }
        item {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text(text = "Nombre", color = MaterialTheme.colors.onBackground)

                } ,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    textColor = MaterialTheme.colors.onBackground
                )
            )
        }
        item {
            Spacer(modifier=Modifier.height((8.dp)))
        }
        item {
            OutlinedTextField(
                value = last_Name,
                onValueChange = {
                    last_Name = it

                },
                label = {
                    Text(text = "Apellido")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((8.dp)))
        }
        item {
            DropdownButton(documentTypes,modifier)
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = document,
                onValueChange = {
                    document = it
                },
                label = {
                    Text(text = "Documento")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it

                },
                label = {
                    Text(text = "Correo electronico")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = cell,
                onValueChange = {
                    cell = it

                },
                label = {
                    Text(text = "Celular")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it

                },
                label = {
                    Text(text = "Contraseña")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it

                },
                label = {
                    Text(text = "Contraseña")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it

                },
                label = {
                    Text(text = "Contraseña")
                }
            )
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
        item {
            Button(onClick = {

            }) {
                Text(text = "Crear cuenta")
            }
        }
        item {
            Spacer(modifier=Modifier.height((16.dp)))
        }
    }
}


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
            label = { Text("Tipo de Documento", color = MaterialTheme.colors.primary) }, // Usa el color del tema
            modifier = modifier
                .clickable { expanded = true }  // Al hacer clic, se abre el menú
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(0.8f),  // Ocupa el 80% del ancho disponible
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
                .fillMaxWidth(0.8f).padding(start = 20.dp, end = 20.dp) // Alinea el menú con el ancho del OutlinedTextField
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




/*
@Composable
@Preview
fun previewSigniupPage(){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        SignupPage()
    }
}


*/