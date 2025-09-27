package com.example.gorodpogod.utils

fun getWeatherText(temp: Int, city: String) = when (temp) {
    in -50..15 -> "Сейчас в г. $city холодно"
    in 16..25 -> "Сейчас в г. $city нормально"
    in 26..50 -> "Сейчас в г. $city жарко"
    else -> "Сейчас в г. $city катастрофа"
}