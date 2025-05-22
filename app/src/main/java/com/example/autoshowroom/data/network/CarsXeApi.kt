package com.example.autoshowroom.data.network

import com.example.autoshowroom.data.model.CarMarketValueResponse
import com.example.autoshowroom.data.model.CarSpecsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsXeApi {

    @GET("marketvalue")
    suspend fun getMarketValue(
        @Query("key") apiKey: String,
        @Query("vin") vin: String
    ): CarMarketValueResponse

    @GET("specs")
    suspend fun getSpecs(
        @Query("key") apiKey: String,
        @Query("vin") vin: String
    ): CarSpecsResponse
}
