package com.example.movil_admin.core.presetation.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movil_admin.core.presetation.composable.NavBar

@Composable
fun GenericLayout(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        bottomBar = {
            NavBar() { selectedIndex ->
                when (selectedIndex) {
                    0 -> println("Agregar seleccionado")
                    1 -> println("Descargar seleccionado")
                    2 -> println("Inicio seleccionado")
                    3 -> println("Salir seleccionado")
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}