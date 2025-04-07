package com.example.movil_admin.core.presetation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movil_admin.ui.theme.NewBlue
import com.example.state.R

@Composable
fun NavBar(modifier: Modifier = Modifier,
           onItemSelected: (Int) -> Unit = {}
)  {
    BottomAppBar (
        modifier = modifier,
        containerColor = NewBlue, // Azul oscuro
        contentColor = Color.White
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp), // Más espacio lateral
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onItemSelected(0) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Agregar",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp) // Aumentamos tamaño del ícono
                )
            }
            IconButton(onClick = { onItemSelected(1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_imbox),
                    contentDescription = "Descargar",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { onItemSelected(2) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Inicio",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { onItemSelected(3) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Salir",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}