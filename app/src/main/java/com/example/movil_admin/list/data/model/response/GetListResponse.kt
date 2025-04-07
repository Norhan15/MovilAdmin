package com.example.movil_admin.list.data.model.response

import com.example.movil_admin.core.data.model.entities.Quote

data class GetListResponse(
    val message: String,
    val quotations: Collection<Quote>
)