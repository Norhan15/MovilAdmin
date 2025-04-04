package com.example.movil_admin.register.domain

import com.example.movil_admin.core.network.TokenManager
import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse
import com.example.movil_admin.register.data.repository.RegisterRepository


class CreateUserUseCase {
    private val repository = RegisterRepository()

    suspend operator fun invoke(
        name: String, email: String, password: String
    ): Result<RegisterResponse> {
        val result = repository.createUser(CreateUserRequest(name, email, password, "admin"))
        if (result.isSuccess) {
            TokenManager.saveToken(result.getOrThrow().token)
        }
        return result;
    }
}