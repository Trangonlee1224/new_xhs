package com.example.new_xhs.Screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.new_xhs.Categories
import com.example.new_xhs.Request.CreateEssayRequest
import com.example.new_xhs.Request.LoginRequest
import com.example.new_xhs.TagEntity
import com.example.new_xhs.UserEntity
import com.example.new_xhs.viewM.AllViewModel
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import java.lang.Thread.sleep


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
    var flag by remember{ mutableStateOf(false)}

    var showDialog by remember { mutableStateOf(false) }

//    LaunchedEffect(pulishResult) {
//        if (pulishResult == true) {
//            showDialog = true
//            // Clear the input fields
//            title.value = ""
//            tags.value = ""
//            content.value = ""
//            abs.value = ""
//        }
//    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        TextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("标题") },
            modifier = Modifier.fillMaxWidth()
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
            label = { Text("摘要") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = tags.value,
            onValueChange = { tags.value = it },
            label = { Text("标签") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = content.value,
            onValueChange = { content.value = it },
            label = { Text("内容") },
            shape = RectangleShape,
            modifier = Modifier.fillMaxWidth()
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
                        flag = true
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

    if (pulishResult==true &&flag == true){
        flag = false
        showDialog = true
        title.value = ""
        tags.value = ""
        content.value = ""
        abs.value = ""
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("发布成功") },
            text = { Text("您的文章已成功发布") },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    content = { Text("确定") }
                )
            }
        )
    }


}