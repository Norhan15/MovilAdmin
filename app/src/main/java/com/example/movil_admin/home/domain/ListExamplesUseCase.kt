package com.example.movil_admin.home.domain

import com.example.movil_admin.home.data.model.reponses.ListExampleResponse
import com.example.movil_admin.home.data.repository.HomeRepository

class ListExamplesUseCase {
    private val repository = HomeRepository()

    suspend operator fun invoke(): Result<ListExampleResponse> {
        return repository.listExamples()
    }
}