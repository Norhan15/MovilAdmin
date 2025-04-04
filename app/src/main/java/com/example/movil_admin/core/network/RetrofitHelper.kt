package com.example.movil_admin.core.network


import com.example.movil_admin.core.network.interceptor.AuthInterceptor
import com.example.movil_admin.core.network.interceptor.HeaderInterceptor
import com.example.movil_admin.core.network.interceptor.LoggingInterceptor
import com.example.movil_admin.register.data.datasource.RegisterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://royalty.protectify.shop/api/"

    private val publicClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .build()

    private val privateClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(AuthInterceptor())
        .build()

    private val publicRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(publicClient)
            .build()
    }

    private val protectedRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(privateClient)
            .build()
    }

    val registerService: RegisterService by lazy {
        publicRetrofit.create(RegisterService::class.java)
    }

}