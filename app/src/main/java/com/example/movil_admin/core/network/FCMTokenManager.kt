package com.example.movil_admin.core.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

object FCMTokenManager {
    private const val PREF_NAME = "TokenPreferences"
    private const val TOKEN_KEY = "auth_token"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String? {
        Log.d("Token Manager", "Fetching Token")
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun saveToken(token: String) {
        Log.d("Token Manager", "Saving Token")
        sharedPreferences.edit() { putString(TOKEN_KEY, token) }
    }

    fun clearToken() {
        Log.d("Token Manager", "Removing Token")
        sharedPreferences.edit() { remove(TOKEN_KEY) }
    }

    fun hasToken(): Boolean {
        return sharedPreferences.getString(TOKEN_KEY, null) !== null
    }

    fun getSharedContext(): SharedPreferences {
        return sharedPreferences
    }
}