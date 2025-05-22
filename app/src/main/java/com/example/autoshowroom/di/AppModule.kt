package com.example.autoshowroom.di

import androidx.room.Room
import com.example.autoshowroom.data.local.AppDatabase
import com.example.autoshowroom.data.network.CarApi
import com.example.autoshowroom.data.network.NewsApi
import com.example.autoshowroom.data.repository.CarRepository
import com.example.autoshowroom.data.repository.NewsRepository
import com.example.autoshowroom.viewmodel.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // --- Интерцептор для Car API ---
    single {
        Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("X-Api-Key", "N4PPpXEguW0b2Z3xJhJHwA==UJ4GzNDWep4xG9LD") // Car API key
                .build()
            chain.proceed(request)
        }
    }

    // --- HTTP клиент ---
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // --- Car API ---
    single {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarApi::class.java)
    }

    // --- News API ---
    single {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/") // ✅ Важно: /v2/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    // --- Room база данных ---
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "autoshowroom_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // --- DAO ---
    single { get<AppDatabase>().bookedCarDao() }
    single { get<AppDatabase>().favoriteCarDao() }

    // --- Репозитории ---
    single { CarRepository(get(), get(), get()) }
    single { NewsRepository(get()) }

    // --- ViewModels ---
    viewModel { CarViewModel(get()) }
    viewModel { CarDetailViewModel(get()) }
    viewModel { BookingViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { NewsViewModel(get()) }
}
