package com.example.mercoapp.ui.pages.seller

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.mercoapp.domain.model.Product
import com.example.mercoapp.ui.components.ActionButton
import com.example.mercoapp.ui.components.CustomTextField
import com.example.mercoapp.ui.components.DropdownButton
import com.example.mercoapp.ui.components.Header
import com.example.mercoapp.ui.components.ProfilePhotoButton
import com.example.mercoapp.viewModel.ProductViewModel
import java.util.UUID



@Composable
fun CreateProductPageSeller(
    modifier: Modifier = Modifier,
    navController: NavController?,
    productViewModel: ProductViewModel = viewModel(),
    sellerId: String // ID del vendedor
) {
    // Variables de estado para los campos
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var productPhotoUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var variety by rememberSaveable { mutableStateOf("") }
    val varieties = listOf("Vainilla", "Chocolate", "Fresa")

    // Lanzador para seleccionar imagen
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        productPhotoUri = uri
    }

    Scaffold(
        topBar = {
            Header(navController = navController,  "Crear Producto",  "productList")
        },
        content = { padding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.height(30.dp)) }

                // Campos para ingresar información
                item {
                    CustomTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nombre del producto"
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    CustomTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = "Descripción del producto"
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    CustomTextField(
                        value = price,
                        onValueChange = { price = it.filter { char -> char.isDigit() || char == '.' } },
                        label = "Precio del producto"
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Dropdown para variedades
                item {
                    DropdownButton(
                        items = varieties
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Botón para cargar imagen
                item {
                    ProfilePhotoButton(launcher = photoLauncher,  "Cargar imagen del producto")
                }

                // Mostrar imagen seleccionada
                item {
                    productPhotoUri?.let { uri ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = rememberImagePainter(data = uri),
                            contentDescription = "Imagen del producto",
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    ActionButton(
                        text = "Guardar producto",
                        onClick = {
                            if (name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && variety.isNotEmpty() && productPhotoUri != null) {
                                val priceValue = price.toDoubleOrNull()
                                if (priceValue == null || priceValue <= 0) {
                                    // Llama a una función en el ViewModel para manejar la validación
                                    productViewModel.onValidationError("Por favor ingrese un precio válido.")
                                    return@ActionButton
                                }

                                productViewModel.uploadProductImage(productPhotoUri!!) { result ->
                                    result.onSuccess { imageUrl ->
                                        val product = Product(
                                            id = UUID.randomUUID().toString(),
                                            name = name,
                                            description = description,
                                            imageUrl = imageUrl,
                                            price = priceValue,
                                            variety = variety,
                                            isActive = true
                                        )
                                        productViewModel.addProductToSeller(sellerId, product)
                                        navController?.navigate("productList")
                                    }.onFailure {
                                        productViewModel.onImageUploadError(it.message ?: "Error desconocido al subir la imagen.")
                                    }
                                }
                            } else {
                                productViewModel.onValidationError("Por favor complete todos los campos y seleccione una imagen.")
                            }
                        },
                        backgroundColor = Color.Blue
                    )
                }


                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CreateProductPagePreview() {
    // Creamos un NavController de prueba
    val navController = rememberNavController()

    // Simulamos un ProductViewModel
    val productViewModel = remember {
        object : ProductViewModel() {
            // Métodos simulados para pruebas
        }
    }

    // Simulamos un ID de vendedor
    val mockSellerId = "seller12345"

    CreateProductPageSeller(
        navController = navController,
        productViewModel = productViewModel,
        sellerId = mockSellerId
    )
}


