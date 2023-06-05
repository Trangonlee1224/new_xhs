package com.example.new_xhs.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.new_xhs.viewM.AllViewModel


@Composable
fun LoginScreen(allviewmodel:AllViewModel, onLogin: () -> Unit){
    var username  = remember {
        mutableStateOf("")
    }
    var password  = remember {
        mutableStateOf("")
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

        Button(
            onClick = {
                allviewmodel.loginUser(username.value, password.value)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                allviewmodel.registerUser(username.value, password.value)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Register")
        }
    }

    val loggedIn by allviewmodel.loggedIn.observeAsState()
    if (loggedIn == true) {
        onLogin.invoke()
    }
}