package com.example.movil_admin.list.data.repository

import com.example.movil_admin.core.network.RetrofitHelper
import com.example.movil_admin.home.data.model.reponses.ListPackResponse
import com.example.movil_admin.list.data.model.request.UpdateQuoteStatus
import com.example.movil_admin.list.data.model.response.GetListResponse
import org.json.JSONObject

class ListRepository {
    private val service = RetrofitHelper.listService

    suspend fun getList(): Result<GetListResponse> {
        return try {
            val response = service.list()
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

    suspend fun updateQuoteStatus(status: UpdateQuoteStatus, id: Int): Result<GetListResponse> {
        return try {
            val response = service.updateStatus(status, id)
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