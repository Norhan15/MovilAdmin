package com.example.movil_admin.register.data.datasource

import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("auth/register")
    suspend fun createUser(@Body request: CreateUserRequest): Response<RegisterResponse>
}