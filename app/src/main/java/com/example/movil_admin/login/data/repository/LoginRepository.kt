package com.example.movil_admin.login.data.repository


import com.example.movil_admin.core.network.RetrofitHelper
import com.example.movil_admin.login.data.model.request.LoginRequest
import com.example.movil_admin.login.data.model.response.LoginResponse
import org.json.JSONObject

class LoginRepository {
    private val service = RetrofitHelper.loginService

    suspend fun login(user: LoginRequest): Result<LoginResponse> {
        return try {
            val response = service.login(user)
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
