package com.example.gorodpogod

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class News(
    val id: Int,
    val category: String,
    val title: String,
    val previewText: String,
    val fullText: String,
    val imageRes: Int,
    val time: String,
    val timeRead: String,
    val isRead: MutableState<Boolean> = mutableStateOf(false),
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)
