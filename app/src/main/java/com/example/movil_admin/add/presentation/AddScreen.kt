package com.example.movil_admin.add.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil_admin.add.presentation.composable.Button
import com.example.movil_admin.add.presentation.composable.FormInput
import com.example.movil_admin.core.presetation.layout.ProtectedLayout
import com.example.movil_admin.ui.theme.NewBlue


@Composable
fun AddScreen(viewModel: AddScreenViewModel = viewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate("home")
        }
    }

    ProtectedLayout(navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Añade recursos",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = NewBlue,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    text = "Paquete",
                    primary = uiState.isFocusOnPackForm,
                    onClick = {
                        viewModel.focusOnPack()
                    }
                )
                Button(
                    text = "Ejemplo",
                    primary = !uiState.isFocusOnPackForm,
                    onClick = {
                        viewModel.focusOnExample()
                    }
                )
            }

            if (uiState.isFocusOnPackForm) {
                FormInput(
                    label = "Nombre del paquete",
                    placeholder = "Nombre del paquete",
                    value = uiState.packName
                ) { value ->
                    viewModel.onPackNameChange(value)
                }
                FormInput(
                    label = "Description",
                    placeholder = "Descripción",
                    value = uiState.packDescription
                ) { value ->
                    viewModel.onPackDescriptionChange(value)
                }
                FormInput(
                    label = "Precio",
                    placeholder = "Precio",
                    numeric = true,
                    value = uiState.packPrice.toString()
                ) { value ->
                    viewModel.onPackPriceChange(value)
                }
                FormInput(
                    label = "Características",
                    placeholder = "Características",
                    textArea = true,
                    value = uiState.packDetails
                ) { value ->
                    viewModel.onPackDetailsChange(value)
                }

                Button(
                    text = "Publicar",
                    primary = true,
                    onClick = { viewModel.createNewPack() },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                FormInput(
                    label = "Nombre del ejemplo",
                    placeholder = "Nombre del ejemplo",
                    value = uiState.exampleName
                ) { value ->
                    viewModel.onExampleNameChange(value)
                }
                FormInput(
                    label = "Enlace",
                    placeholder = "www.website.com",
                    value = uiState.exampleLink
                ) { value ->
                    viewModel.onExampleLinkChange(value)
                }
                Button(text = "Cargar imagen", primary = false, onClick = {

                })
                Button(
                    text = "Publicar",
                    primary = true,
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }

        }
    }
}