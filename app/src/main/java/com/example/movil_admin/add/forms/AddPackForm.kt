package com.example.movil_admin.add.forms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movil_admin.add.composable.Button
import com.example.movil_admin.add.composable.FormInput

@Composable
fun AddPackForm (){
    FormInput(label = "Nombre del paquete", placeholder = "Nombre del paquete")
    FormInput(label = "Description", placeholder = "Descripción")
    FormInput(label = "Precio", placeholder = "Precio", numeric = true)
    FormInput(label = "Características", placeholder = "Características", textArea = true)

    Spacer(modifier = Modifier.height(140.dp))

    Button(
        text = "Publicar",
        primary = true,
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
}