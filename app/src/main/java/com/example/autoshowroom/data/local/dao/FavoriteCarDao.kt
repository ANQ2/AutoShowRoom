package com.example.autoshowroom.data.local.dao

import androidx.room.*
import com.example.autoshowroom.data.local.entity.FavoriteCar
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(car: FavoriteCar)

    @Query("DELETE FROM favorite_cars WHERE make = :make AND model = :model")
    suspend fun deleteFavoriteByMakeModel(make: String, model: String)

    @Query("SELECT * FROM favorite_cars")
    fun getAllFavorites(): Flow<List<FavoriteCar>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cars WHERE make = :make AND model = :model)")
    suspend fun isFavorite(make: String, model: String): Boolean
}
