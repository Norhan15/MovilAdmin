package com.example.movil_admin.login.data.datasource

import com.example.movil_admin.login.data.model.request.LoginRequest
import com.example.movil_admin.login.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}