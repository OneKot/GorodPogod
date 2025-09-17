package com.example.gorodpogod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gorodpogod.ui.theme.Gray1A1A1A
import com.example.gorodpogod.ui.theme.Gray343434

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

enum class Screen {
    WEATHER, NEWS, FAVORITES
}

@Preview
@Composable
fun WeatherApp() {
    var currentScreen by remember { mutableStateOf(Screen.WEATHER) }
    var history by remember { mutableStateOf(listOf<String>()) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        when (currentScreen) {
            Screen.WEATHER -> WeatherScreen(
                modifier = Modifier.fillMaxSize().padding(bottom = 72.dp),
                history = history,
                onHistoryChange = { history = it }
            )
            Screen.NEWS -> NewsScreen(modifier = Modifier.fillMaxSize().padding(bottom = 72.dp))
            Screen.FAVORITES -> FavoritesScreen(modifier = Modifier.fillMaxSize().padding(bottom = 72.dp))
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
    }
}

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
                .padding(start = 16.dp, top = 24.dp)
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
                    focusedIndicatorColor = Gray343434,
                    unfocusedIndicatorColor = Gray343434,
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
                    focusedIndicatorColor = Gray343434,
                    unfocusedIndicatorColor = Gray343434,
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

@Composable
fun WeatherResult(city: String, tempWithSign: String, result: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray1A1A1A, shape = RoundedCornerShape(4.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = Gray1A1A1A, shape = RoundedCornerShape(4.dp))
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(city.uppercase(), color = Color.White)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DeviceThermostat, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(tempWithSign, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray))
        Spacer(modifier = Modifier.height(8.dp))
        Text(result, color = Color.White)
    }
}


@Composable
fun SearchHistory(history: List<String>) {
    Text("Недавно вы искали:", fontSize = 16.sp, color = Color.White, modifier = Modifier.fillMaxWidth().padding(start = 2.dp))
    Spacer(modifier = Modifier.height(6.dp))

    if (history.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(33.dp)
                .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Здесь появятся ваши предыдущие запросы", color = Color.LightGray, fontSize = 14.sp)
        }
    } else {
        LazyColumn {
            items(history) { item ->
                val parts = item.split(": ")
                val cityName = parts.getOrElse(0) { "" }
                val tempWithSign = parts.getOrElse(1) { "" }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(Gray1A1A1A, RoundedCornerShape(6.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(cityName, color = Color.White)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DeviceThermostat, contentDescription = null, tint = Color.White)
                        Text(tempWithSign, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavButton(
    painter: Painter,
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.White, RoundedCornerShape(6.dp))
            .background(if (isSelected) Color.Gray else Color.Black, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter, contentDescription = text, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text, color = Color.White, fontSize = 12.sp)
        }
    }
}

fun getWeatherText(temp: Int, city: String) = when (temp) {
    in -50..15 -> "Сейчас в г. $city холодно"
    in 16..25 -> "Сейчас в г. $city нормально"
    in 26..50 -> "Сейчас в г. $city жарко"
    else -> "Сейчас в г. $city катастрофа"
}

@Composable
fun NewsScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Новости", color = Color.White)
    }
}

@Composable
fun FavoritesScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Избранное", color = Color.White)
    }
}
