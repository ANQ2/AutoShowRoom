package com.example.autoshowroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoshowroom.data.model.Article
import com.example.autoshowroom.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    fun loadNews() {
        viewModelScope.launch {
            try {
                _articles.value = repository.fetchTeslaNews().articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
