package com.example.movil_admin.core.presetation.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.movil_admin.core.network.TokenManager
import com.example.movil_admin.core.presetation.composable.NavBar

@Composable
fun ProtectedLayout(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    val token = TokenManager.getToken()

    LaunchedEffect (token) {
        if (token == null) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true } // limpia el backstack
            }
        }
    }

    if (token != null) {
        Scaffold(
            bottomBar = {
                NavBar() { selectedIndex ->
                    when (selectedIndex) {
                        0 -> navController.navigate("add")
                        1 -> navController.navigate("list")
                        2 -> {
                            navController.navigate("home") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                        3 -> {
                            TokenManager.clearToken()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}
