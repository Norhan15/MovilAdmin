package com.example.movil_admin.add.composable

import android.widget.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movil_admin.ui.theme.NewBlue

@Composable
fun Button(text: String, primary: Boolean = true, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val buttonColors = if (primary) {
        ButtonDefaults.buttonColors(containerColor = NewBlue)
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = NewBlue
        )
    }

    val border = if (!primary) {
        ButtonDefaults.outlinedButtonBorder.copy(
            1.dp,
            Brush.linearGradient(listOf(NewBlue, NewBlue)) //Argument type mismatch: actual type is 'androidx.compose.ui.graphics.Brush', but 'androidx.compose.ui.unit.Dp' was expected.
        )
    } else {
        null
    }

    androidx.compose.material3.Button(
        onClick = onClick,
        colors = buttonColors,
        border = border,
        modifier = modifier
    ) {
        Text(text = text)
    }
}