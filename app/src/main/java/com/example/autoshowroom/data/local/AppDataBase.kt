package com.example.autoshowroom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.autoshowroom.data.local.dao.BookedCarDao
import com.example.autoshowroom.data.local.dao.FavoriteCarDao
import com.example.autoshowroom.data.local.entity.BookedCar
import com.example.autoshowroom.data.local.entity.FavoriteCar

@Database(
    entities = [BookedCar::class, FavoriteCar::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookedCarDao(): BookedCarDao
    abstract fun favoriteCarDao(): FavoriteCarDao
}
