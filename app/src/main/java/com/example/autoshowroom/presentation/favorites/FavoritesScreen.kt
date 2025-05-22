package com.example.autoshowroom.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoshowroom.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(onShowDetails: (String, String) -> Unit) {
    val viewModel: FavoritesViewModel = koinViewModel()
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Избранные автомобили", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(favorites) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onShowDetails(car.make, car.model) }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${car.make} ${car.model}", style = MaterialTheme.typography.titleMedium)
                        car.year?.let { Text("Год: $it") }
                        car.engine?.let { Text("Двигатель: $it") }
                        car.transmission?.let { Text("Коробка: $it") }
                        car.fuelType?.let { Text("Топливо: $it") }
                        car.price?.let { Text("Цена: $$it") }
                    }
                }
            }
        }
    }
}
