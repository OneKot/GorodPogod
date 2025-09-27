package com.example.gorodpogod.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gorodpogod.utils.Colors
import com.example.gorodpogod.utils.Colors.SurfaceDark

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
                        .background(SurfaceDark, RoundedCornerShape(6.dp))
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