package com.example.movil_admin.login.data.datasource

import com.example.movil_admin.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/login/")
    suspend fun login(@Body credentials: Map<String, String>): Response<LoginResponse>
}