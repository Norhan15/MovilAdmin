package com.example.movil_admin.login.domain

import com.example.movil_admin.login.data.repository.AuthRepository

class LoginUseCase {
    private val authRepository = AuthRepository()

    suspend operator fun invoke(username: String, password: String){//: Result<String> {
//        return authRepository.login(username, password)
    }
}
