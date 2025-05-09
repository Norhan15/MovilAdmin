package com.example.movil_admin.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil_admin.core.presetation.layout.ProtectedLayout
import com.example.movil_admin.home.presentation.composable.ExampleCard
import com.example.movil_admin.home.presentation.composable.PackCard
import com.example.movil_admin.ui.theme.NewBlue
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope {
            val packsDeferred = async { viewModel.loadPacks() }
            val examplesDeferred = async { viewModel.loadExamples() }

            // Esperar resultados (opcional si no necesitas esperar)
            packsDeferred.await()
            examplesDeferred.await()
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetFetched()
        }
    }

    ProtectedLayout(navController) { paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            // Aplicamos los paddingValues para respetar el NavBar
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección de Paquetes
            item {
                Text(
                    text = "Paquetes",
                    style = MaterialTheme.typography.displayLarge,
                    color = NewBlue,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            itemsIndexed(uiState.packs.toList()) { _, pack ->
                PackCard(
                    pack = pack,
                    onDelete = {
                        id ->
                        coroutineScope.launch {
                            viewModel.removePack(id)
                        }
                    }
                )
            }

            // Sección de Ejemplos
            item {
                Text(
                    text = "Ejemplos",
                    style = MaterialTheme.typography.displayLarge,
                    color = NewBlue,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            // Lista de Ejemplos
            itemsIndexed(uiState.examples.toList()) {_, example -> //
                ExampleCard(
                    example = example,
                    onDelete = {
                        id ->
                        coroutineScope.launch {
                            viewModel.removeExample(id)
                        }
                    }
                )
            }
        }
    }
}

// Extensión para usar una lista directamente con items()
private fun <T> LazyListScope.items(
    items: List<T>,
    itemContent: @Composable (T) -> Unit
) {
    items(
        count = items.size,
        key = { index -> items[index].hashCode() }
    ) { index ->
        itemContent(items[index])
    }
}