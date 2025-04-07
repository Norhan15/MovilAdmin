package com.example.movil_admin.add.domain

import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import com.example.movil_admin.add.data.repository.AddRepository

class CreatePackUseCase {
    private val repository = AddRepository()

    suspend operator fun invoke(
        name: String, description: String, details: String, price: Float
    ): Result<CreatePackResponse> {
        return repository.createPack(CreatePackRequest(name, description, details, price));
    }
}