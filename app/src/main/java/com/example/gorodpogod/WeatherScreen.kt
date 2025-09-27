package com.example.gorodpogod.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gorodpogod.components.WeatherResult
import com.example.gorodpogod.components.SearchHistory
import com.example.gorodpogod.utils.Colors.BackgroundDark
import com.example.gorodpogod.utils.getWeatherText

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    history: List<String>,
    onHistoryChange: (List<String>) -> Unit
) {
    var city by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var temperature by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var submitted by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    val tempValue = temperature.text.toIntOrNull() ?: 0
    val tempWithSign = if (tempValue > 0) "+${tempValue}°C" else "${tempValue}°C"

    Column(modifier = modifier.padding(7.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Погода",
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 38.dp, bottom = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!submitted) {
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Город", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = BackgroundDark,
                    unfocusedIndicatorColor = BackgroundDark,
                    unfocusedContainerColor = Color.Black,
                    focusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = temperature,
                onValueChange = { temperature = it },
                label = { Text("Температура (°C)", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = BackgroundDark,
                    unfocusedIndicatorColor = BackgroundDark,
                    unfocusedContainerColor = Color.Black,
                    focusedContainerColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val temp = temperature.text.toIntOrNull()
                    if (temp != null) {
                        result = getWeatherText(temp, city.text)
                        onHistoryChange(history + "${city.text}: $tempWithSign")
                        submitted = true
                    } else {
                        result = "Введите корректную температуру"
                    }
                },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                )
            ) {
                Text("Оценить", fontSize = 14.sp)
            }
        } else {
            WeatherResult(city.text, tempWithSign, result)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    submitted = false
                    city = TextFieldValue("")
                    temperature = TextFieldValue("")
                    result = ""
                },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Сделать новый запрос", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SearchHistory(history)
    }
}