package com.example.autoshowroom.data.model

data class CarMarketValueResponse(
    val success: Boolean,
    val market_value: String?,
    val make: String?,
    val model: String?,
    val year: String?
)