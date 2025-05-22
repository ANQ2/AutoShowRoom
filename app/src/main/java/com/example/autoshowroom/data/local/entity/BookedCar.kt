package com.example.autoshowroom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booked_cars")
data class BookedCar(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val make: String,
    val model: String,
    val year: String?,
    val engine: String?,
    val transmission: String?,
    val fuelType: String?,
    val price: Int?
)
