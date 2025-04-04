package com.example.movil_admin.register.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movil_admin.ui.theme.ButtonBlue
import com.example.movil_admin.ui.theme.ButtonWhite
import com.example.movil_admin.ui.theme.Coral
import com.example.movil_admin.ui.theme.NewBlue
import com.example.movil_admin.ui.theme.White


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Parte azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(NewBlue),
            contentAlignment = Alignment.CenterStart // Centra verticalmente y alinea a la izquierda
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp), // Agrega margen izquierdo para separar del borde
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Crear Cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    color = White
                )
                Text(
                    text = "Crear una nueva cuenta para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = White
                )
            }
        }

// Parte blanca con bordes redondeados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(
                    color = White, shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp
                    ) // Bordes redondeados
                )
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Inputs
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Adjusted spacing
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Adjusted spacing
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp) // Extra space before buttons
            )

            // Buttons
            Button(
                onClick = {
                    viewModel.register()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RectangleShape, // Hacer los bordes cuadrados
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBlue, contentColor = ButtonWhite
                )
            ) {
                Text("Crear cuenta", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(24.dp)) // Más espacio entre los botones

            OutlinedButton(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RectangleShape, // Hacer los bordes cuadrados
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ButtonBlue
                ),
                border = BorderStroke(1.dp, ButtonBlue)
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.labelLarge)
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = error, color = Coral, modifier = Modifier.padding(top = 16.dp)
                )
            }

            LaunchedEffect(uiState.isSuccess) {
                if (uiState.isSuccess) {
                    onRegisterSuccess()
                }
            }
        }
    }
}