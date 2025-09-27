package com.example.gorodpogod.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gorodpogod.News
import com.example.gorodpogod.R
import kotlinx.coroutines.delay

@Composable
fun FavoriteNewsCard(
    news: News,
    onRemoveAfterDelay: (News) -> Unit,
    onClick: () -> Unit
) {
    var isFavoriteState by remember { mutableStateOf(news.isFavorite.value) }
    var isRemoving by remember { mutableStateOf(false) }

    LaunchedEffect(isRemoving) {
        if (isRemoving) {
            delay(2000)
            news.isFavorite.value = false
            onRemoveAfterDelay(news)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clickable { onClick() }
            .border(1.dp, Color(0xFF343434), RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(news.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = news.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xFF343434), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(news.category, color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (!isRemoving) {
                        isFavoriteState = false
                        isRemoving = true
                    }
                },
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.Black, RoundedCornerShape(4.dp))
            ) {
                Icon(
                    painter = painterResource(
                        if (isFavoriteState) R.drawable.favorite else R.drawable.unfavorite
                    ),
                    contentDescription = "Избранное",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
