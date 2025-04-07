package com.example.movil_admin.core.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

object TokenManager {

    private const val PREF_NAME = "TokenPreferences"
    private const val TOKEN_KEY = "auth_token"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun saveToken(token: String) {
        Log.d("Admin", "Saving token $token")
        sharedPreferences.edit() { putString(TOKEN_KEY, token) }
    }

    fun clearToken() {
        sharedPreferences.edit() { remove(TOKEN_KEY) }
    }
}