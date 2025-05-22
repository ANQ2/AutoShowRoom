package com.example.autoshowroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoshowroom.data.local.entity.BookedCar
import com.example.autoshowroom.data.repository.CarRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookingViewModel(private val repo: CarRepository) : ViewModel() {
    val bookings: StateFlow<List<BookedCar>> = repo.getBookedCars()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun removeBooking(make: String, model: String) {
        viewModelScope.launch {
            repo.removeBooking(make, model)
        }
    }
}
