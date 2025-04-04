package com.example.movil_admin.add.forms

import androidx.compose.runtime.Composable
import com.example.movil_admin.add.composable.FormInput

@Composable
fun AddExampleForm (){
    FormInput(label = "Nombre del ejemplo", placeholder = "Nombre del ejemplo")
    FormInput(label = "Enlace", placeholder = "www.website.com")
}