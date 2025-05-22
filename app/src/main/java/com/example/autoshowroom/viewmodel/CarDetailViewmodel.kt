package com.example.autoshowroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoshowroom.data.local.entity.BookedCar
import com.example.autoshowroom.data.model.CarResponse
import com.example.autoshowroom.data.repository.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarDetailViewModel(private val repo: CarRepository) : ViewModel() {

    private val _specs = MutableStateFlow<CarResponse?>(null)
    val specs: StateFlow<CarResponse?> = _specs

    fun loadSpecs(make: String, model: String) {
        viewModelScope.launch {
            val result = repo.fetchCarSpecs(make, model)
            _specs.value = result
        }
    }

    fun bookTestDrive(car: CarResponse) {
        viewModelScope.launch {
            val bookedCar = BookedCar(
                make = car.make,
                model = car.model,
                year = car.year?.toString(),
                engine = car.engine,
                transmission = car.transmission,
                fuelType = car.fuel_type,
                price = car.msrp
            )
            repo.bookCar(bookedCar)
        }
    }
}
