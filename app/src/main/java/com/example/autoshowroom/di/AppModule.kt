package com.example.autoshowroom.di

import androidx.room.Room
import com.example.autoshowroom.data.local.AppDatabase
import com.example.autoshowroom.data.network.CarApi
import com.example.autoshowroom.data.repository.CarRepository
import com.example.autoshowroom.viewmodel.BookingViewModel
import com.example.autoshowroom.viewmodel.CarDetailViewModel
import com.example.autoshowroom.viewmodel.CarViewModel
import com.example.autoshowroom.viewmodel.FavoritesViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // API Interceptor
    single {
        Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("X-Api-Key", "N4PPpXEguW0b2Z3xJhJHwA==UJ4GzNDWep4xG9LD")
                .build()
            chain.proceed(request)
        }
    }

    // HTTP client
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarApi::class.java)
    }

    // Room database
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "autoshowroom_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    single { get<AppDatabase>().bookedCarDao() }
    single { get<AppDatabase>().favoriteCarDao() }

    // Repository
    single { CarRepository(get(), get(), get()) } // api, bookedDao, favoriteDao

    // ViewModels
    viewModel { CarViewModel(get()) }
    viewModel { CarDetailViewModel(get()) }
    viewModel { BookingViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}
