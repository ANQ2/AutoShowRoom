package com.example.autoshowroom.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoshowroom.viewmodel.CarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    onShowDetails: (String, String) -> Unit,
    onShowBookings: () -> Unit,
    onShowMap: () -> Unit,
    onShowFavorites: () -> Unit
) {
    val viewModel: CarViewModel = koinViewModel()
    val cars by viewModel.cars.collectAsState()

    var make by remember { mutableStateOf("toyota") }
    var model by remember { mutableStateOf("") }
    var bodyStyle by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadCars(make, model)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Фильтр автомобилей", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = make, onValueChange = { make = it }, label = { Text("Марка") })
        OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Модель") })
        OutlinedTextField(value = bodyStyle, onValueChange = { bodyStyle = it }, label = { Text("Тип кузова") })

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.loadCars(make, model) }, modifier = Modifier.weight(1f)) {
                Text("Поиск")
            }
            Button(onClick = onShowBookings, modifier = Modifier.weight(1f)) {
                Text("Мои брони")
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onShowMap, modifier = Modifier.weight(1f)) {
                Text("Карта автосалонов")
            }
            Button(onClick = onShowFavorites, modifier = Modifier.weight(1f)) {
                Text("Избранное ❤️")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(cars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onShowDetails(car.make, car.model) }
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${car.make} ${car.model} (${car.year})", style = MaterialTheme.typography.titleMedium)
                        car.msrp?.let { Text("Цена: $$it") }
                        car.vehicleClass?.let { Text("Кузов: $it") }
                        car.engine?.let { engine -> Text("Двигатель: $engine") }
                    }
                }
            }
        }
    }
}
