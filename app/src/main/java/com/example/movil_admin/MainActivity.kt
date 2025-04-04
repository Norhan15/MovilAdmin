package com.example.movil_admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movil_admin.core.navigation.NavigationWrapper
import com.example.movil_admin.core.network.TokenManager
import com.example.movil_admin.ui.theme.StateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        enableEdgeToEdge()
        setContent {
            StateTheme {
                NavigationWrapper()
            }
        }
    }
}

