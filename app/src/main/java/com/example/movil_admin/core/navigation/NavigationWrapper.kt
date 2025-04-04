package com.example.movil_admin.core.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movil_admin.add.presentation.AddScreen
import com.example.movil_admin.home.presentation.HomeScreen
import com.example.movil_admin.list.presentation.ListScreen
import com.example.movil_admin.login.presentation.LoginScreen
import com.example.movil_admin.login.presentation.LoginViewModel
import com.example.movil_admin.register.presentation.RegisterScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {

        composable("Login") {
            val viewModel: LoginViewModel = viewModel() // Obtén el ViewModel proporcionado por el framework
            LoginScreen(
                viewModel = viewModel,
                navController = navController
            ){
                navController.navigate("home")
            }
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home") },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("list") {
            ListScreen(navController = navController)
        }

        composable("add") {
            AddScreen(navController = navController)
        }
    }
}