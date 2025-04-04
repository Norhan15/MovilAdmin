package com.example.movil_admin.login.data.repository


import com.example.movil_admin.core.network.RetrofitHelper
import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse
import org.json.JSONObject

class AuthRepository {
    private val service = RetrofitHelper.registerService

    suspend fun createUser(user: CreateUserRequest): Result<RegisterResponse> {
        return try {
            val response = service.createUser(user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorMessage = response.errorBody()?.string()
                val error = if (errorMessage.isNullOrEmpty()) {
                    "Ha ocurrido un error inesperado"
                } else {
                    try {
                        val errorJson = JSONObject(errorMessage)
                        val message =
                            errorJson.optString("message", "Ha ocurrido un error inesperado")
                        message
                    } catch (e: Exception) {
                        "Ha ocurrido un error inesperado"
                    }
                }
                Result.failure(Exception(error))
            }
        } catch (e: Exception) {
            Result.failure(e);
        }
    }
}
