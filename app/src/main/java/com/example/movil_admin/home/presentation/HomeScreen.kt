package com.example.movil_admin.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movil_admin.core.presetation.composable.NavBar
import com.example.movil_admin.core.presetation.layout.GenericLayout
import com.example.movil_admin.home.presentation.composable.ExampleCard
import com.example.movil_admin.home.presentation.composable.PackCard
import com.example.movil_admin.ui.theme.NewBlue

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect (Unit) {
        viewModel.loadPacks()
        viewModel.loadExamples()
    }

    DisposableEffect  (Unit) {
        onDispose {
            viewModel.resetFetched()
        }
    }

    GenericLayout() {
        Column {
            Text(text = "Paquetes", style = MaterialTheme.typography.displayLarge, color = NewBlue)
            Column {
                for(pack in uiState.packs){
                    PackCard(pack)
                }
            }
            Text(text = "Ejemplos", style = MaterialTheme.typography.displayLarge, color = NewBlue)
            Column {
                for(example in uiState.examples){
                    ExampleCard(example){}
                }
            }
        }
    }
}