package com.example.autoshowroom.data.network

import com.example.autoshowroom.data.model.CarResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi {
    @GET("v1/cars")
    suspend fun getCars(
        @Query("make") make: String,
        @Query("model") model: String
    ): List<CarResponse>
}
