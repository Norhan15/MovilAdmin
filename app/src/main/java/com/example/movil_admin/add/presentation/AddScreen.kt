package com.example.movil_admin.add.presentation

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movil_admin.add.presentation.composable.Button
import com.example.movil_admin.add.presentation.composable.FormInput
import com.example.movil_admin.core.presetation.layout.ProtectedLayout
import com.example.movil_admin.core.provider.MediaProvider
import com.example.movil_admin.ui.theme.NewBlue


@Composable
fun AddScreen(viewModel: AddScreenViewModel = viewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val mediaProvider = remember { MediaProvider(context) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.updateImageUri(mediaProvider.getTempImageUri())
        } else {
            viewModel.setError("No se pudo capturar la imagen")
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateImageUri(uri)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(mediaProvider.getImageCaptureUri())
        } else {
            viewModel.setError("Se requiere permiso de cámara para tomar fotos")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaProvider.cleanupTempFiles()
        }
    }

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
                    text = "Paquete", primary = uiState.isFocusOnPackForm, onClick = {
                        viewModel.focusOnPack()
                    })
                Button(
                    text = "Ejemplo", primary = !uiState.isFocusOnPackForm, onClick = {
                        viewModel.focusOnExample()
                    })
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
                    modifier = Modifier.fillMaxWidth()
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
                    label = "Enlace", placeholder = "www.website.com", value = uiState.exampleLink
                ) { value ->
                    viewModel.onExampleLinkChange(value)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(text = "Cargar imagen", primary = false, onClick = {
                        viewModel.setLoading(true)
                        imagePickerLauncher.launch("image/*")
                    })

                    Button(text = "Tomar imagen", primary = false, onClick = {
                        viewModel.setLoading(true)
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    })
                }

                // Mostrar la imagen seleccionada
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else if (uiState.imageUri != null) {
                        AsyncImage(
                            model = uiState.imageUri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("No hay imagen seleccionada")
                    }
                }

                Button(
                    text = "Publicar", primary = true, onClick = {
                        viewModel.createNewExample(context)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
