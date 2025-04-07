package com.example.movil_admin.list.domain

import com.example.movil_admin.home.data.model.reponses.ListExampleResponse
import com.example.movil_admin.home.data.repository.HomeRepository
import com.example.movil_admin.list.data.model.response.GetListResponse
import com.example.movil_admin.list.data.repository.ListRepository

class ListQuoteUseCase{
    private val repository = ListRepository()

    suspend operator fun invoke(): Result<GetListResponse> {
        return repository.getList()
    }
}