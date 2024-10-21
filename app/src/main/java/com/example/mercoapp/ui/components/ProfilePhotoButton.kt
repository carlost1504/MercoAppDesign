package com.example.mercoapp.ui.components

// ProfilePhotoButton.kt
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import com.example.mercoapp.ui.theme.redMerco

@Composable
fun ProfilePhotoButton(launcher: ActivityResultLauncher<String>,textImage:String) {
    Button(
        onClick = { launcher.launch("image/*") },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = redMerco),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp)
    ) {
        Text(
            text = textImage,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
