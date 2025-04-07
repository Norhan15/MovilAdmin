package com.example.movil_admin.list.data.source

import com.example.movil_admin.list.data.model.request.UpdateQuoteStatus
import com.example.movil_admin.list.data.model.response.GetListResponse
import com.example.movil_admin.login.data.model.request.LoginRequest
import com.example.movil_admin.login.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListService {
    @GET("quotations")
    suspend fun list(): Response<GetListResponse>

    @PUT("quotations/{id}/status")
    suspend fun updateStatus(
        @Body request: UpdateQuoteStatus,
        @Path("id") id: Int
    ): Response<GetListResponse>
}