package com.example.mercoapp.ui.components

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TabRow() {
    androidx.compose.material3.TabRow(selectedTabIndex = 0) {
        Tab(selected = true, onClick = { /*TODO*/ }, text = { Text("Panadería") })
        Tab(selected = false, onClick = { /*TODO*/ }, text = { Text("Verduras") })
        Tab(selected = false, onClick = { /*TODO*/ }, text = { Text("Más categorías") })
    }
}