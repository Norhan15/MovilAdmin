package com.example.movil_admin.list.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movil_admin.core.presetation.layout.ProtectedLayout

@Composable
fun ListScreen(navController: NavController){
    ProtectedLayout(navController) {
        Text(text = "List")
    }
}