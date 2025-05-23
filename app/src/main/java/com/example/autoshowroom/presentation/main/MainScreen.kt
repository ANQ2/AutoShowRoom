package com.example.autoshowroom.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.autoshowroom.viewmodel.CarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    onShowDetails: (String, String) -> Unit,
    onShowBookings: () -> Unit,
    onShowMap: () -> Unit,
    onShowFavorites: () -> Unit,
    onShowNews: () -> Unit
) {
    val viewModel: CarViewModel = koinViewModel()
    val cars by viewModel.cars.collectAsState()

    var make by remember { mutableStateOf("toyota") }
    var model by remember { mutableStateOf("") }
    var bodyStyle by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadCars(make, model)
    }

    Column(modifier = Modifier.padding(35.dp)) {
        Text("Фильтр автомобилей", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = make, onValueChange = { make = it }, label = { Text("Марка") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = model, onValueChange = { model = it }, label = { Text("Модель") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = bodyStyle, onValueChange = { bodyStyle = it }, label = { Text("Тип кузова") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButtonWithLabel(Icons.Default.Search, "Поиск") { viewModel.loadCars(make, model) }
            IconButtonWithLabel(Icons.Default.ListAlt, "Брони") { onShowBookings() }
            IconButtonWithLabel(Icons.Default.Map, "Карта") { onShowMap() }
            IconButtonWithLabel(Icons.Default.Favorite, "Избранное") { onShowFavorites() }
            IconButtonWithLabel(Icons.Default.Article, "Новости") { onShowNews() }
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onShowDetails(car.make, car.model) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${car.make} ${car.model} (${car.year})", style = MaterialTheme.typography.titleMedium)
                        car.msrp?.let { Text("Цена: $$it") }
                        car.vehicleClass?.let { Text("Кузов: $it") }
                        car.engine?.let { Text("Двигатель: $it") }
                    }
                }
            }
        }
    }
}

@Composable
fun IconButtonWithLabel(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = label)
        }
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
