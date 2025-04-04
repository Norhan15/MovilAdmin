package com.example.movil_admin.home.domain

import com.example.movil_admin.home.data.model.reponses.ListPackResponse
import com.example.movil_admin.home.data.model.reponses.ResourceDeletedResponse
import com.example.movil_admin.home.data.repository.HomeRepository

class RemovePackUseCase {
    private val repository = HomeRepository()

    suspend operator fun invoke(id: Int): Result<ResourceDeletedResponse> {
        return repository.removePack(id)
    }
}