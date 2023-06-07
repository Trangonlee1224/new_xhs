package com.example.new_xhs.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.new_xhs.viewM.AllViewModel


@Composable
fun LoginScreen(allviewmodel:AllViewModel, onLogin: () -> Unit){
    var username  = remember {
        mutableStateOf("")
    }

    val loggedIn by allviewmodel.loggedIn.observeAsState()
    val regiser_res by allviewmodel.register_res.observeAsState()

    var password  = remember {
        mutableStateOf("")
    }

    val rshowDialogv = remember {
        mutableStateOf(false)
    }

    val lshowDialogv = remember {
        mutableStateOf(false)
    }

    val rfshowDialogv = remember{
        mutableStateOf(false)
    }

    fun lshowDialog(){
        lshowDialogv.value = true
    }

    fun lhideDialog(){
        lshowDialogv.value = false
    }

    fun rshowDialog(){
        rshowDialogv.value = true
    }

    fun rhideDialog(){
        rshowDialogv.value = false
    }


    fun rfshowDialog(){
        rfshowDialogv.value = true
    }

    fun rfhideDialog(){
        rfshowDialogv.value = false
    }

    if(lshowDialogv.value) {
        AlertDialog(
            onDismissRequest = { lshowDialogv.value = false },
            title = { Text("登录失败") },
            text = { Text("登录用户失败，请检查用户名或密码") },
            confirmButton = {
                Button(
                    onClick = {
                        lshowDialogv.value = false
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                Button(
                    onClick = { lshowDialogv.value = false }
                ) {
                    Text("取消")
                }
            }
        )
    }

    if (rshowDialogv.value) {
        AlertDialog(
            onDismissRequest = { rshowDialogv.value = false },
            title = { Text("注册成功") },
            text = { Text("注册用户成功，请返回登录") },
            confirmButton = {
                Button(
                    onClick = {
                        rshowDialogv.value = false
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                Button(
                    onClick = { rshowDialogv.value = false }
                ) {
                    Text("取消")
                }
            }
        )
    }

    if (rfshowDialogv.value) {
        AlertDialog(
            onDismissRequest = { rfshowDialogv.value = false },
            title = { Text("注册失败") },
            text = { Text("注册用户失败，请更改用户名") },
            confirmButton = {
                Button(
                    onClick = {
                        rfshowDialogv.value = false
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                Button(
                    onClick = { rfshowDialogv.value = false }
                ) {
                    Text("取消")
                }
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.Center) {
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    allviewmodel.loginUser(username.value, password.value)
                    if(loggedIn==false){
                        lshowDialog()
                    }
                }
            ) {
                Text("登录")
            }

            Button(
                onClick = {
                    allviewmodel.registerUser(username.value, password.value)
                    if (regiser_res==200){
                        rshowDialog()
                    }

                    else if (regiser_res==400){
                        rfshowDialog()
                    }
                }
            ) {
                Text("注册")
            }
        }

    }



    if (loggedIn == true) {
        onLogin.invoke()
    }
}