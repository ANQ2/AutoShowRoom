package com.example.autoshowroom.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.autoshowroom.data.model.CarResponse

@Entity(tableName = "favorite_cars")
data class FavoriteCar(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val make: String,
    val model: String,
    val year: String?,
    val engine: String?,
    val transmission: String?,
    val fuelType: String?,
    val price: Int?
) {
    companion object {
        fun fromResponse(car: CarResponse): FavoriteCar {
            return FavoriteCar(
                make = car.make,
                model = car.model,
                year = car.year?.toString(),
                engine = car.engine,
                transmission = car.transmission,
                fuelType = car.fuel_type,
                price = car.msrp
            )
        }
    }
}
