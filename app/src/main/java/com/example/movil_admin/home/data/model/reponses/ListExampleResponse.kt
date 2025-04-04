package com.example.movil_admin.home.data.model.reponses

import com.example.movil_admin.home.data.model.entities.Example
import com.example.movil_admin.home.data.model.entities.Pack

data class ListExampleResponse(
    val message:String,
    val examples: Collection<Example>
)
