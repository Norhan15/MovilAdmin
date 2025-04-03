package com.example.movil_admin.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movil_admin.ui.theme.ButtonBlue
import com.example.movil_admin.ui.theme.ButtonWhite
import com.example.movil_admin.ui.theme.Coral
import com.example.movil_admin.ui.theme.NewBlue
import com.example.movil_admin.ui.theme.White

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
    onNavigateToNotes: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val username by viewModel.username.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val errorMessage by viewModel.errorMessage.observeAsState(null)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val navigationCommand by viewModel.navigationCommand.observeAsState()

    LaunchedEffect(navigationCommand) {
        when (navigationCommand) {
            "Notes" -> onNavigateToNotes()
            "Register" -> onNavigateToRegister()
        }
        viewModel.onNavigationHandled()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Parte azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(NewBlue),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.headlineMedium,
                    color = White
                )
                Text(
                    text = "Iniciar sesión para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = White
                )
            }
        }

        // Parte blanca con bordes redondeados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Inputs
            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Correo") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            // Botones
            OutlinedButton(
                onClick = { viewModel.onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ButtonBlue
                ),
                border = BorderStroke(1.dp, ButtonBlue)
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.navigateToRegister() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBlue,
                    contentColor = ButtonWhite
                )
            ) {
                Text("Crear cuenta", style = MaterialTheme.typography.labelLarge)
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Coral,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}