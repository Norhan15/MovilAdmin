package com.example.movil_admin.core.data.model.entities

data class Quote(
    val id: Int,
    val buyer_name: String,
    val email: String,
    val phone_number: String,
    val pack_id: Int,
    val user_id: Int,
    val technical_support: Boolean,
    val logo_design: Boolean,
    val custom_template: Boolean,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val pack: Pack
)