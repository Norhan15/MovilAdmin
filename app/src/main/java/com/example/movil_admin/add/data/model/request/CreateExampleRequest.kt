package com.example.movil_admin.add.data.model.request

import android.net.Uri

data class CreateExampleRequest(
    val name: String,
    val link: String,
    val image: Uri?
)
