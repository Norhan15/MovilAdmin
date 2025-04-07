package com.example.movil_admin.home.data.model.reponses

import com.example.movil_admin.core.data.model.entities.Example

data class ListExampleResponse(
    val message:String,
    val examples: Collection<Example>
)
