package com.example.movil_admin.register.data.repository


import com.example.movil_admin.core.network.RetrofitHelper
import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse


class RegisterRepository()  {
    private val registerService = RetrofitHelper.registerService

    suspend fun createUser(user: CreateUserRequest): Result<RegisterResponse> {
        return try {
            val response = registerService.createUser(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}