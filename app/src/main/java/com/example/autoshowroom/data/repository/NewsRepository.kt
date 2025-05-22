package com.example.autoshowroom.data.repository

import com.example.autoshowroom.data.model.NewsResponse
import com.example.autoshowroom.data.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val api: NewsApi) {
    suspend fun fetchTeslaNews(): NewsResponse = withContext(Dispatchers.IO) {
        api.getTeslaNews()
    }
}
