package com.example.gorodpogod

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel(), modifier: Modifier) {
    val newsList = viewModel.newsList
    val selectedNews = viewModel.selectedNews

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Новости",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 38.dp)
            )
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp, bottom = 109.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(newsList) { news ->
                    NewsCard(
                        news = news,
                        onClick = { viewModel.selectedNews = news },
                        onRead = { news.isRead.value = !news.isRead.value },
                        onFavorite = { news.isFavorite.value = !news.isFavorite.value }
                    )
                }
            }
        }

        if (selectedNews != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )

            FullNewsScreen(
                news = selectedNews,
                onClose = { viewModel.selectedNews = null },
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun NewsCard(news: News, onRead: () -> Unit, onFavorite: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (news.isRead.value) Color(0xFF262626) else Color(0xFF1A1A1A)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(color = if (news.isRead.value) Color(0xFF262626) else Color(0xFF1A1A1A),
                            RoundedCornerShape(4.dp))
                        .height(32.dp)
                        .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(news.category, color = Color.White, fontSize = 14.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(news.time, color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(8.dp))
            Divider(color = Color.DarkGray, thickness = 1.dp)

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Статья", color = Color.Gray, fontSize = 14.sp)
                Text(news.timeRead, color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(Modifier.height(4.dp))
            Image(
                painter = painterResource(news.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(Modifier.height(8.dp))
            Text(news.title, color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(4.dp))
            Text(news.previewText, color = Color.LightGray, maxLines = 3, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, lineHeight = 24.sp)

            Spacer(Modifier.height(8.dp))
            Divider(color = Color.DarkGray, thickness = 1.dp)

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = onRead,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(32.dp).height(24.dp)
                ) {
                    Icon(
                        painter = painterResource(if (news.isRead.value) R.drawable.read_status else R.drawable.unread_status),
                        contentDescription = "Прочитано",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedButton(
                    onClick = onFavorite,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(32.dp).height(24.dp)
                ) {
                    Icon(
                        painter = painterResource(if (news.isFavorite.value) R.drawable.favorite else R.drawable.unfavorite),
                        contentDescription = "Избранное",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun FullNewsScreen(news: News, onClose: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(top = 38.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (news.isRead.value) Color(0xFF262626) else Color(0xFF1A1A1A)
                )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (news.isRead.value) Color(0xFF262626) else Color(0xFF1A1A1A),
                                    RoundedCornerShape(4.dp)
                                )
                                .height(32.dp)
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(news.category, color = Color.White, fontSize = 14.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                            Icon(
                                Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(news.time, color = Color.White, fontSize = 14.sp)
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(Modifier.height(8.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Статья", color = Color.Gray, fontSize = 14.sp)
                        Text(news.timeRead, color = Color.Gray, fontSize = 14.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                    Image(
                        painter = painterResource(news.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(160.dp)
                    )

                    Spacer(Modifier.height(8.dp))
                    Text(news.title, color = Color.White, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(news.fullText, color = Color.LightGray, fontSize = 12.sp)

                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Gray, thickness = 1.dp)
                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { news.isRead.value = !news.isRead.value },
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.width(32.dp).height(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (news.isRead.value) R.drawable.read_status
                                    else R.drawable.unread_status
                                ),
                                contentDescription = "Прочитано",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        OutlinedButton(
                            onClick = { news.isFavorite.value = !news.isFavorite.value },
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.width(32.dp).height(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (news.isFavorite.value) R.drawable.favorite
                                    else R.drawable.unfavorite
                                ),
                                contentDescription = "Избранное",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 6.dp)
                    .size(32.dp)
                    .background(Color.White, shape = RoundedCornerShape(4.dp))
                    .clickable { onClose() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Закрыть",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}