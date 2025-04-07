package com.example.movil_admin.add.domain

import android.content.Context
import android.net.Uri
import com.example.movil_admin.add.data.model.request.CreateExampleRequest
import com.example.movil_admin.add.data.model.request.CreatePackRequest
import com.example.movil_admin.add.data.model.response.CreateExampleResponse
import com.example.movil_admin.add.data.model.response.CreatePackResponse
import com.example.movil_admin.add.data.repository.AddRepository

class CreateExampleUseCase {
    private val repository = AddRepository()

    suspend operator fun invoke(
        name: String, link: String, media: Uri?, context: Context
    ): Result<CreateExampleResponse> {
        return repository.createExample(CreateExampleRequest(name, link, media), context)
    }
}