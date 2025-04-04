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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil_admin.add.composable.Button
import com.example.movil_admin.add.composable.FormInput
import com.example.movil_admin.add.forms.AddExampleForm
import com.example.movil_admin.add.forms.AddPackForm
import com.example.movil_admin.core.presetation.layout.ProtectedLayout
import com.example.movil_admin.ui.theme.NewBlue


@Composable
fun AddScreen(viewModel: AddScreenViewModel = viewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()

    ProtectedLayout (navController) {
        Column (
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

            if(uiState.isFocusOnPackForm){
                AddPackForm()
            }else{
                AddExampleForm()
            }

        }
    }
}