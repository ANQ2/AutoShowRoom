package com.example.autoshowroom.data.model

data class CarSpecsResponse(
    val success: Boolean,
    val make: String?,
    val model: String?,
    val year: String?,
    val engine: String?,
    val transmission: String?,
    val fuel_type: String?,
    val image: String?,

    val msrp: Int? = null
)
