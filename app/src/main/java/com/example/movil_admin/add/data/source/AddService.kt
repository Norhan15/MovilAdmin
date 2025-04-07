package com.example.movil_admin.add.data.source

import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreateExampleResponse
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AddService {
    @POST("packs/add")
    suspend  fun createPack(@Body request: CreatePackRequest): Response<CreatePackResponse>

    @Multipart
    @POST("examples")
    suspend fun createExample(
        @Part("name") name: RequestBody,
        @Part("link") link: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CreateExampleResponse>
}