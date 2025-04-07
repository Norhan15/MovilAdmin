package com.example.movil_admin.core.data.model.entities

import android.telecom.Call.Details

data class Pack (
    val id: Int,
    val name: String,
    val description: String,
    val details: String,
    val price: Float,
    val created_at:String,
    val updated_at: String
)