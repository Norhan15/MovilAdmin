package com.example.movil_admin.list.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movil_admin.core.presetation.layout.ProtectedLayout
import com.example.movil_admin.home.presentation.HomeViewModel
import com.example.movil_admin.home.presentation.composable.PackCard
import com.example.movil_admin.list.presentation.composable.QuotationsCard
import com.example.movil_admin.ui.theme.NewBlue
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@Composable
fun ListScreen(navController: NavController, viewModel: ListViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope {
            val quotationsDeferred = async { viewModel.loadQuoteList() }
            quotationsDeferred.await()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetFetched()
        }
    }

    ProtectedLayout(navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .padding(bottom = 16.dp)
                .padding(horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = "Cotizaciones",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = NewBlue,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                // Aplicamos los paddingValues para respetar el NavBar
                contentPadding = PaddingValues(
                    top = 16.dp, bottom = paddingValues.calculateBottomPadding() + 16.dp
                ), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(uiState.quotations.toList()) { _, quote ->
                    QuotationsCard(quote, onApprove = {
                        coroutineScope.launch {
                            viewModel.updateQuoteStatus("accepted", quote.id)
                        }
                    }, onReject = {
                        coroutineScope.launch {
                            viewModel.updateQuoteStatus("rejected", quote.id)
                        }
                    }, onContact = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:${quote.email}".toUri()
                        }
                        context.startActivity(intent)
                    })
                }
            }
        }
    }
}