package com.example.movil_admin.core.provider

import com.example.state.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.movil_admin.MainActivity
import com.example.movil_admin.core.network.FCMTokenManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseProvider:FirebaseMessagingService() {
    fun getFCMToken(onTokenReceived: (String?) -> Unit) {
        if (FCMTokenManager.hasToken()) {
            onTokenReceived(FCMTokenManager.getToken())
            return
        }

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM token failed", task.exception)
                    onTokenReceived(null) // Retorna null en caso de error
                    return@addOnCompleteListener
                }
                // Token obtenido con éxito
                val token = task.result
                Log.d("FCM", "Token: $token")
                FCMTokenManager.saveToken(token)
                onTokenReceived(token)
            }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nuevo token: $token")
        FCMTokenManager.saveToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Mensaje recibido: ${remoteMessage.data}")

        val title = remoteMessage.notification?.title ?: "Notificación"
        val message = remoteMessage.notification?.body ?: "Tienes un nuevo mensaje"

        showNotification(title, message)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(title: String, message: String) {
        val channelId = "fcm_default_channel"

        // Crear un intent para abrir la app al tocar la notificación
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // Crear la notificación
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_home) // Usa un ícono en drawable
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear el canal de notificación para Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones FCM",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, builder.build())
    }

}
