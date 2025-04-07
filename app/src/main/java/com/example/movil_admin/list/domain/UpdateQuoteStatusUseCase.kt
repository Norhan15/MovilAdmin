package com.example.movil_admin.list.domain

import com.example.movil_admin.list.data.model.request.UpdateQuoteStatus
import com.example.movil_admin.list.data.model.response.GetListResponse
import com.example.movil_admin.list.data.repository.ListRepository

class UpdateQuoteStatusUseCase {
    private val repository = ListRepository()

    suspend operator fun invoke(status: String, id: Int): Result<GetListResponse> {
        return repository.updateQuoteStatus(UpdateQuoteStatus(status), id)
    }
}