package com.example.gorodpogod.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gorodpogod.NewsViewModel
import com.example.gorodpogod.ui.components.CategoryCard
import com.example.gorodpogod.ui.components.FavoriteNewsCard
import com.example.gorodpogod.utils.Strings

@Composable
fun FavoritesScreen(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    var selectedCategory by remember { mutableStateOf("Все") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = Strings.FAVORITES_TITLE,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(top = 22.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        val categories = listOf("Все", "Культура", "Технологии", "Путешествия")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                CategoryCard(
                    category = category,
                    isSelected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        val favorites = viewModel.newsList.filter { it.isFavorite.value }
        val filtered = if (selectedCategory == "Все") favorites
        else favorites.filter { it.category == selectedCategory }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered, key = { it.id }) { news ->
                FavoriteNewsCard(
                    news = news,
                    onClick = { viewModel.selectedNews = news },
                    onRemoveAfterDelay = { removedNews ->
                    }
                )
            }
        }
    }
}
