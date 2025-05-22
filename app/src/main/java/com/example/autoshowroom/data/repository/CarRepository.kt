package com.example.autoshowroom.data.repository

import com.example.autoshowroom.data.local.dao.BookedCarDao
import com.example.autoshowroom.data.local.dao.FavoriteCarDao
import com.example.autoshowroom.data.local.entity.BookedCar
import com.example.autoshowroom.data.local.entity.FavoriteCar
import com.example.autoshowroom.data.model.CarResponse
import com.example.autoshowroom.data.network.CarApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CarRepository(
    private val api: CarApi,
    private val bookedCarDao: BookedCarDao,
    private val favoriteCarDao: FavoriteCarDao
) {

    suspend fun fetchCars(make: String, model: String): List<CarResponse> = withContext(Dispatchers.IO) {
        api.getCars(make, model)
    }

    suspend fun fetchCarSpecs(make: String, model: String): CarResponse? = withContext(Dispatchers.IO) {
        api.getCars(make, model).firstOrNull()
    }

    suspend fun bookCar(car: BookedCar) = withContext(Dispatchers.IO) {
        bookedCarDao.insertBooking(car)
    }

    fun getBookedCars(): Flow<List<BookedCar>> {
        return bookedCarDao.getAllBookings()
    }

    suspend fun addToFavorites(car: FavoriteCar) = withContext(Dispatchers.IO) {
        favoriteCarDao.insertFavorite(car)
    }

    suspend fun removeFromFavorites(car: CarResponse) = withContext(Dispatchers.IO) {
        favoriteCarDao.deleteFavoriteByMakeModel(car.make, car.model)
    }

    suspend fun isFavorite(make: String, model: String): Boolean = withContext(Dispatchers.IO) {
        favoriteCarDao.isFavorite(make, model)
    }

    fun getFavoriteCars(): Flow<List<FavoriteCar>> = favoriteCarDao.getAllFavorites()

    suspend fun removeBooking(make: String, model: String) = withContext(Dispatchers.IO) {
        bookedCarDao.deleteBookingByMakeModel(make, model)
    }
}
