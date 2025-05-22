package com.example.autoshowroom.data.local.dao

import androidx.room.*
import com.example.autoshowroom.data.local.entity.BookedCar
import kotlinx.coroutines.flow.Flow

@Dao
interface BookedCarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(car: BookedCar)

    @Query("SELECT * FROM booked_cars")
    fun getAllBookings(): Flow<List<BookedCar>>

    @Query("DELETE FROM booked_cars WHERE make = :make AND model = :model")
    suspend fun deleteBookingByMakeModel(make: String, model: String)
}