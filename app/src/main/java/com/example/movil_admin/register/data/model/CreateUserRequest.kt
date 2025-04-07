package com.example.movil_admin.register.data.model

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val fcm_token: String
)