package com.example.movil_admin.home.data.source

import com.example.movil_admin.home.data.model.reponses.ListExampleResponse
import com.example.movil_admin.home.data.model.reponses.ListPackResponse
import com.example.movil_admin.home.data.model.reponses.ResourceDeletedResponse
import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {
    @GET("packs")
    suspend fun listPacks(): Response<ListPackResponse>
    @GET("examples")
    suspend fun listExamples(): Response<ListExampleResponse>

    @DELETE("packs/{id}")
    suspend fun removePack(@Path("id") id: Int): Response<ResourceDeletedResponse>
    @DELETE("examples/{id}")
    suspend fun removeExample(@Path("id") id: Int): Response<ResourceDeletedResponse>
}