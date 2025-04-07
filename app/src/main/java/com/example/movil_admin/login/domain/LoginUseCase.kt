package com.example.movil_admin.login.domain

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.movil_admin.core.network.FCMTokenManager
import com.example.movil_admin.core.network.TokenManager
import com.example.movil_admin.login.data.model.request.LoginRequest
import com.example.movil_admin.login.data.model.response.LoginResponse
import com.example.movil_admin.login.data.repository.LoginRepository

class LoginUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> {
        val result = repository.login(LoginRequest(email, password, FCMTokenManager.getToken() ?: ""))
        if (result.isSuccess) {
            TokenManager.saveToken(result.getOrThrow().token)
        }
        return result;
    }
}
