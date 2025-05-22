package com.example.autoshowroom.data.model

import com.google.gson.annotations.SerializedName

data class CarResponse(
    val make: String,
    val model: String,
    val year: Int?,
    val engine: String?,
    val transmission: String?,
    val fuel_type: String?,         // 🔄 warning можно проигнорировать, API использует snake_case
    val drivetrain: String?,
    val msrp: Int? = null,

    @SerializedName("class")
    val vehicleClass: String?       // ✅ это поле кузова, из поля "class"
)
