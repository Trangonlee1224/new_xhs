package com.example.new_xhs.Screen

import android.preference.PreferenceScreen
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.new_xhs.EssayEntity
import com.example.new_xhs.UserEntity
import com.example.new_xhs.viewM.AllViewModel
import com.example.new_xhs.Request.DeleteEssayRequest


@Composable
fun PersonScreen(allviewmodel:AllViewModel){
    val essayEntities by allviewmodel.essayEntities.observeAsState(emptyList())
    val ausername by allviewmodel.Ausername.observeAsState()
    val auserid by allviewmodel.Auserid.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Username: $ausername",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Text(
            text = "Articles:",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(essayEntities) { essayentity ->
                ArticleItem(essayentity, ausername, allviewmodel)
            }
        }
    }
}


@Composable
fun ArticleItem(essayentity: EssayEntity, ausername: String?, allviewmodel: AllViewModel) {
    val ausername by allviewmodel.Ausername.observeAsState()
    val auserid by allviewmodel.Auserid.observeAsState()

    var expanded by remember { mutableStateOf(false) } // 控制展开与收起状态的状态变量

    val passage_id = essayentity.passage_id
    val user = UserEntity(auserid,ausername)
    val content = essayentity.content
    val abs = essayentity.abs
    val title = essayentity.title
    val published = essayentity.published
    val category = essayentity.category
    val tags = essayentity.tags
    val createtime = essayentity.createTime
    val updatetime = essayentity.updateTime
    val cover_photo = essayentity.coverPhoto
    val detelerequest = DeleteEssayRequest(passage_id, user, content, abs, title, published, category, tags, createtime, updatetime, cover_photo)
    Log.d("deleterequest is:", detelerequest.toString())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded } // 点击时切换展开与收起状态
    ) {
        if (essayentity.user.username == ausername) {
            Text(
                text = "Title: ${essayentity.title}",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            )

            if (expanded) {
                Text(
                    text = "Content: ${essayentity.content}",
                    style = TextStyle(fontSize = 14.sp),
                )

                Button(
                    onClick = {
                        allviewmodel.deleteEssay(detelerequest)
                        allviewmodel.fetchArticles()
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "删除",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    )
                }
            }
        }
    }
}