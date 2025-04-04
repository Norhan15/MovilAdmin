package com.example.movil_admin.home.presentation.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movil_admin.home.data.model.entities.Pack

@Composable
fun PackCard(pack: Pack) {
    Text(text = pack.name)
}