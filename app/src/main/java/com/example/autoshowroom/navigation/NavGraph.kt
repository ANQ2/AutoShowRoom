package com.example.autoshowroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.autoshowroom.presentation.main.MainScreen
import com.example.autoshowroom.presentation.detail.CarDetailScreen
import com.example.autoshowroom.presentation.bookings.MyBookingsScreen
import com.example.autoshowroom.presentation.map.DealershipMapScreen
import com.example.autoshowroom.presentation.favorites.FavoritesScreen
import com.example.autoshowroom.presentation.news.NewsScreen // ✅ добавлено

object Routes {
    const val MAIN = "main"
    const val DETAIL = "detail/{make}/{model}"
    const val BOOKINGS = "bookings"
    const val MAP = "map"
    const val FAVORITES = "favorites"
    const val NEWS = "news" // ✅ добавлено
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.MAIN) {
        composable(Routes.MAIN) {
            MainScreen(
                onShowDetails = { make, model ->
                    navController.navigate("detail/$make/$model")
                },
                onShowBookings = {
                    navController.navigate(Routes.BOOKINGS)
                },
                onShowMap = {
                    navController.navigate(Routes.MAP)
                },
                onShowFavorites = {
                    navController.navigate(Routes.FAVORITES)
                },
                onShowNews = { // ✅ новая кнопка
                    navController.navigate(Routes.NEWS)
                }
            )
        }
        composable(Routes.DETAIL) { backStackEntry ->
            val make = backStackEntry.arguments?.getString("make") ?: ""
            val model = backStackEntry.arguments?.getString("model") ?: ""
            CarDetailScreen(make = make, model = model)
        }
        composable(Routes.BOOKINGS) {
            MyBookingsScreen()
        }
        composable(Routes.MAP) {
            DealershipMapScreen()
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                onShowDetails = { make, model ->
                    navController.navigate("detail/$make/$model")
                }
            )
        }
        composable(Routes.NEWS) { // ✅ экран новостей
            NewsScreen()
        }
    }
}
