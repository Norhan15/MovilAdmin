package com.example.movil_admin.add.data.model.response

import android.os.Message
import com.example.movil_admin.core.data.model.entities.Pack

data class CreatePackResponse(
    val message: String,
    val pack: Pack
)