package com.example.new_xhs.Screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.new_xhs.Categories
import com.example.new_xhs.Request.CreateEssayRequest
import com.example.new_xhs.Request.LoginRequest
import com.example.new_xhs.TagEntity
import com.example.new_xhs.UserEntity
import com.example.new_xhs.viewM.AllViewModel



@Composable
fun AddScreen(allviewmodel:AllViewModel){
    val categories = listOf(
        "123" to 1,
        "234" to 2,
    )
    val pulishResult by allviewmodel.publishResult.observeAsState()

    val ausername by allviewmodel.Ausername.observeAsState()
    val auserid by allviewmodel.Auserid.observeAsState()
    val password by allviewmodel.password.observeAsState()

    val selectedCategory = remember { mutableStateOf(categories.first()) }
    val title = remember { mutableStateOf("") }
    val tags = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val abs = remember { mutableStateOf("") }


    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("标题") }
        )

        Box(modifier = Modifier
            .wrapContentSize()
            .clickable { expanded = true }
        ) {
            Text(text = "分类: ${selectedCategory.value.first}")

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { (category, id) ->
                    DropdownMenuItem(onClick = {
                        selectedCategory.value = category to id
                        expanded = false
                    }) {
                        Text(text = category)
                    }
                }
            }
        }

        TextField(
            value = abs.value,
            onValueChange = { abs.value = it },
            label = { Text("摘要") }
        )

        TextField(
            value = tags.value,
            onValueChange = { tags.value = it },
            label = { Text("标签") }
        )

        TextField(
            value = content.value,
            onValueChange = { content.value = it },
            label = { Text("内容") },
//            modifier = Modifier.fillMaxHeight()
        )

        Button(
            onClick = {
                if (title.value.isNotBlank() && tags.value.isNotBlank() && content.value.isNotBlank()){
                    val userentity = UserEntity(auserid,ausername)
                    val category = Categories(selectedCategory.value.second,selectedCategory.value.first)

                    val tagsentity = TagEntity(null, tags.value)
                    val t:List<TagEntity> = listOf(tagsentity)

                    val cRequest = CreateEssayRequest(null, userentity, content.value, abs.value,
                        title.value, null, category, t , null,null,null)
                    Log.d("CreateEssayRequest:", cRequest.toString())
                    if (title.value.isNotBlank() && tags.value.isNotBlank() && content.value.isNotBlank()){
//                        allviewmodel.loginUser(ausername, password)
                        allviewmodel.AddEssay(cRequest)
                    }
                    else{
                        /*  这里放警告  */
                    }

                } },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("发布")
        }
    }


}