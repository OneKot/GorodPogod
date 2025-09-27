package com.example.gorodpogod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gorodpogod.screens.*
import com.example.gorodpogod.components.BottomNavButton
import com.example.gorodpogod.ui.screens.FavoritesScreen

enum class Screen {
    WEATHER, NEWS, FAVORITES
}

@Preview
@Composable
fun WeatherApp(viewModel: NewsViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf(Screen.WEATHER) }
    var history by remember { mutableStateOf(listOf<String>()) }

    val selectedNews = viewModel.selectedNews

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when (currentScreen) {
            Screen.WEATHER -> WeatherScreen(
                modifier = Modifier.fillMaxSize().padding(bottom = 72.dp),
                history = history,
                onHistoryChange = { history = it }
            )
            Screen.NEWS -> NewsScreen(
                modifier = Modifier.fillMaxSize().padding(bottom = 72.dp),
                viewModel = viewModel
            )
            Screen.FAVORITES -> FavoritesScreen(
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize().padding(bottom = 72.dp)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            BottomNavButton(
                painter = painterResource(id = R.drawable.weather),
                text = "Погода",
                isSelected = currentScreen == Screen.WEATHER,
                modifier = Modifier.weight(1f)
            ) { currentScreen = Screen.WEATHER }

            BottomNavButton(
                painter = painterResource(id = R.drawable.news),
                text = "Новости",
                isSelected = currentScreen == Screen.NEWS,
                modifier = Modifier.weight(1f)
            ) { currentScreen = Screen.NEWS }

            BottomNavButton(
                painter = painterResource(id = R.drawable.heart),
                text = "Избранное",
                isSelected = currentScreen == Screen.FAVORITES,
                modifier = Modifier.weight(1f)
            ) { currentScreen = Screen.FAVORITES }
        }

        if (selectedNews != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
            FullNewsScreen(
                news = selectedNews,
                onClose = { viewModel.selectedNews = null },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight(0.85f)
            )
        }
    }
}