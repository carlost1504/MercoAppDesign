package com.example.mercoapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mercoapp.R
import com.example.mercoapp.ui.pages.buyer.ProductItem

@Composable
fun ProductList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item { ProductItem("Pasteles", R.drawable.ic_launcher_background) }
        item { ProductItem("Panes", R.drawable.ic_launcher_background) }
        item { ProductItem("Fritos", R.drawable.ic_launcher_background) }
        item { ProductItem("Otros", R.drawable.ic_launcher_background) }
    }
}