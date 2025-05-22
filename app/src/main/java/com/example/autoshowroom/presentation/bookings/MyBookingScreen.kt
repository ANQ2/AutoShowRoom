package com.example.autoshowroom.presentation.bookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoshowroom.viewmodel.BookingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyBookingsScreen() {
    val viewModel: BookingViewModel = koinViewModel()
    val bookings by viewModel.bookings.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Мои брони", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(bookings) { car ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Марка: ${car.make}")
                        Text("Модель: ${car.model}")
                        car.year?.let { Text("Год: $it") }
                        car.engine?.let { Text("Двигатель: $it") }
                        car.transmission?.let { Text("Коробка: $it") }

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(onClick = {
                            scope.launch {
                                viewModel.removeBooking(car.make, car.model)
                            }
                        }) {
                            Text("Удалить бронь ❌")
                        }
                    }
                }
            }
        }
    }
}
