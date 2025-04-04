package com.example.movil_admin.home.data.model.reponses

import com.example.movil_admin.home.data.model.entities.Pack

data class ListPackResponse(
    val message:String,
    val packs: Collection<Pack>
)
