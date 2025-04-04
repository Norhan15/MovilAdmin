package com.example.movil_admin.add.data.source

import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddService {
    @POST("packs/add")
    suspend  fun createPack(@Body request: CreatePackRequest): Response<CreatePackResponse>

}