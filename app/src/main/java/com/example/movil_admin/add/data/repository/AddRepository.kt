package com.example.movil_admin.add.data.repository

import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import com.example.movil_admin.core.network.RetrofitHelper

class AddRepository() {
    private val service = RetrofitHelper.addService

    suspend fun createPack(user: CreatePackRequest): Result<CreatePackResponse> {
        return try {
            val response = service.createPack(user)
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