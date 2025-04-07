package com.example.movil_admin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movil_admin.core.navigation.NavigationWrapper
import com.example.movil_admin.core.network.FCMTokenManager
import com.example.movil_admin.core.network.TokenManager
import com.example.movil_admin.core.provider.FirebaseProvider
import com.example.movil_admin.ui.theme.StateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        FCMTokenManager.init(this)
        FirebaseProvider().getFCMToken { token ->
            if (token != null) {
                Log.d("FCM", "Token recibido: $token")
            } else {
                Log.e("FCM", "No se pudo obtener el token")
            }
        }
        enableEdgeToEdge()
        setContent {
            StateTheme {
                NavigationWrapper()
            }
        }
    }
}

