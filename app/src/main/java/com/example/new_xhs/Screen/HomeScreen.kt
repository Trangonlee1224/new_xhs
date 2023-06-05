package com.example.new_xhs.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.new_xhs.EssayEntity
import com.example.new_xhs.viewM.AllViewModel

@Composable
fun HomeScreen(allviewmodel:AllViewModel) {

    val essayEntities by allviewmodel.essayEntities.observeAsState(emptyList())
    allviewmodel.fetchArticles()

    val selectedCategory = remember { mutableStateOf(essayEntities.firstOrNull()?.category?.category_name) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "新闻分类") },
            backgroundColor = Color.White,
            elevation = 4.dp
        )

        // 分类滚动栏
        val categories = essayEntities.map { it.category.category_name }.distinct()
        LazyRow(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory.value
                Text(
                    text = category,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { selectedCategory.value = category },
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // 文章列表
        val filteredEntities =
            essayEntities.filter { it.category.category_name == selectedCategory.value }
        val selectedCardIndex = remember { mutableStateOf((-1)) }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(filteredEntities) { index, essayEntity ->
                EssayEntityCard(
                    essayEntity = essayEntity,
                    onClick = { selectedCardIndex.value = index},
                    isExpanded = selectedCardIndex.value == index
                )
            }
        }
    }
}

@Composable
fun EssayEntityCard(essayEntity: EssayEntity, onClick:() -> Unit, isExpanded: Boolean) {
    // 控制展示具体内容的状态变量
    // 使用Clickable组件处理点击事件
    Card(
        modifier = Modifier.padding(16.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    )
    {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .scale(if (isExpanded) 1.2f else 1.0f) // 根据isExpanded参数应用缩放效果
        ) {
            Column {
                Text(
                    text = essayEntity.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (isExpanded) {
                    Text(
                        text = essayEntity.content,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}


