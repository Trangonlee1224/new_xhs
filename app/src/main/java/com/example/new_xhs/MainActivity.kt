package com.example.new_xhs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.new_xhs.ui.theme.New_xhsTheme
import com.example.new_xhs.Screen.AddScreen
import com.example.new_xhs.Screen.HomeScreen
import com.example.new_xhs.Screen.LoginScreen
import com.example.new_xhs.Screen.PersonScreen
import com.example.new_xhs.viewM.AllViewModel

class MainActivity : ComponentActivity() {
//    private val authViewModel: AuthViewModel by viewModels()
//    private val articleViewModel: ArticleViewModel by viewModels()
//    private val addViewModel:AddViewModel by viewModels()
    private val allviewmodel:AllViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            New_xhsTheme {
// A surface container using the 'background' color from the theme

                LoginScreen(allviewmodel, onLogin = {
                    setContent{
                        MyScreen(allviewmodel)
                    }
                })
            }
        }
    }
}


@Composable
fun MyScreen(allViewModel: AllViewModel){
    val navController = rememberNavController()
    var currentRoute by remember(navController) { mutableStateOf("home") }

    Scaffold(
        // 主要内容区域
        content = {
            Box(modifier = Modifier.padding(bottom = 56.dp)) {
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(allViewModel) }
                    composable("publish") { AddScreen(allViewModel) }
                    composable("profile") { PersonScreen(allViewModel) }
                }
            }
        },
        // 底部导航栏
        bottomBar = {
            BottomNavigation(modifier = Modifier.height(56.dp)) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val destinations = listOf(
                    "home" to Icons.Filled.Home,
                    "publish" to Icons.Filled.Add,
                    "profile" to Icons.Filled.Person
                )
                destinations.forEach { (route, icon) ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                icon,
                                contentDescription = route
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                        onClick = {
                            currentRoute = route
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    )
}