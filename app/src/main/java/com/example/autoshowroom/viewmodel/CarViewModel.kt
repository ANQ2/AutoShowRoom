package com.example.autoshowroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoshowroom.data.repository.CarRepository
import com.example.autoshowroom.data.model.CarResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarViewModel(private val repository: CarRepository) : ViewModel() {

    private val _cars = MutableStateFlow<List<CarResponse>>(emptyList())
    val cars: StateFlow<List<CarResponse>> get() = _cars

    fun loadCars(make: String, model: String) {
        viewModelScope.launch {
            try {
                _cars.value = repository.fetchCars(make, model)
            } catch (e: Exception) {
                _cars.value = emptyList()
            }
        }
    }
}
