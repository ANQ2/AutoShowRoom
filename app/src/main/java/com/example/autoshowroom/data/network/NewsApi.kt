package com.example.autoshowroom.data.network

import com.example.autoshowroom.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getTeslaNews(
        @Query("q") query: String = "tesla",
        @Query("from") from: String = "2025-04-22",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = "6d3381f760ae49b4aa56e828df5cb24b"
    ): NewsResponse
}
