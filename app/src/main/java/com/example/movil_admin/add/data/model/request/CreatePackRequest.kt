package com.example.movil_admin.add.data.model.request

data class CreatePackRequest(
    val name: String,
    val description: String,
    val details: String,
    val price: Float,
)
