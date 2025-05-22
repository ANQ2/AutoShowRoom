package com.example.autoshowroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoshowroom.data.local.entity.FavoriteCar
import com.example.autoshowroom.data.model.CarResponse
import com.example.autoshowroom.data.repository.CarRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repo: CarRepository) : ViewModel() {

    val favorites: StateFlow<List<FavoriteCar>> = repo.getFavoriteCars()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun isFavorite(make: String, model: String): Boolean {
        return repo.isFavorite(make, model)
    }

    fun toggleFavorite(car: CarResponse) {
        viewModelScope.launch {
            val exists = repo.isFavorite(car.make, car.model)
            if (exists) {
                repo.removeFromFavorites(car)
            } else {
                repo.addToFavorites(FavoriteCar.fromResponse(car))
            }
        }
    }
}
