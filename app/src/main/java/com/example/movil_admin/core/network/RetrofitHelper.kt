package com.example.movil_admin.core.network


import com.example.movil_admin.add.data.source.AddService
import com.example.movil_admin.core.network.interceptor.AuthInterceptor
import com.example.movil_admin.core.network.interceptor.HeaderInterceptor
import com.example.movil_admin.core.network.interceptor.LoggingInterceptor
import com.example.movil_admin.home.data.source.HomeService
import com.example.movil_admin.login.data.datasource.LoginService
import com.example.movil_admin.register.data.datasource.RegisterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "http://192.168.100.13:8000/api/"

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

    val loginService: LoginService by lazy {
        publicRetrofit.create(LoginService::class.java)
    }

    val homeService: HomeService by lazy {
        protectedRetrofit.create(HomeService::class.java)
    }

    val addService: AddService by lazy {
        protectedRetrofit.create(AddService::class.java)
    }

}