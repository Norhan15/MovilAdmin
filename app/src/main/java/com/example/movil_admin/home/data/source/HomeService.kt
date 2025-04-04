package com.example.movil_admin.home.data.source

import com.example.movil_admin.home.data.model.reponses.ListExampleResponse
import com.example.movil_admin.home.data.model.reponses.ListPackResponse
import com.example.movil_admin.register.data.model.CreateUserRequest
import com.example.movil_admin.register.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeService {
    @GET("packs")
    suspend fun listPacks(): Response<ListPackResponse>
    @GET("examples")
    suspend fun listExamples(): Response<ListExampleResponse>
}