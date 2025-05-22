package com.example.autoshowroom.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoshowroom.viewmodel.CarDetailViewModel
import com.example.autoshowroom.viewmodel.FavoritesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CarDetailScreen(make: String, model: String) {
    val viewModel: CarDetailViewModel = koinViewModel()
    val favoritesViewModel: FavoritesViewModel = koinViewModel()

    val specs by viewModel.specs.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var isBooked by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadSpecs(make, model)
        isFavorite = favoritesViewModel.isFavorite(make, model)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        specs?.let {
            Text("Марка: ${it.make}")
            Text("Модель: ${it.model}")
            it.year?.let { year -> Text("Год: $year") }
            it.vehicleClass?.let { vc -> Text("Кузов: $vc") }
            it.engine?.let { eng -> Text("Двигатель: $eng") }
            it.transmission?.let { tr -> Text("Коробка: $tr") }
            it.fuel_type?.let { fuel -> Text("Топливо: $fuel") }
            it.msrp?.let { price -> Text("Цена: $$price") }

            Button(onClick = {
                coroutineScope.launch {
                    viewModel.bookTestDrive(it)
                    isBooked = true
                }
            }) {
                Text(if (isBooked) "Забронировано ✅" else "Забронировать тест-драйв")
            }

            Button(onClick = {
                coroutineScope.launch {
                    favoritesViewModel.toggleFavorite(it)
                    isFavorite = !isFavorite
                }
            }) {
                Text(if (isFavorite) "Удалить из избранного ❌" else "Добавить в избранное ❤️")
            }
        } ?: Text("Загрузка...")
    }
}
