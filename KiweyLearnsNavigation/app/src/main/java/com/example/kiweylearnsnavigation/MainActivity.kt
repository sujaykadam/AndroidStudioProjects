package com.example.kiweylearnsnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kiweylearnsnavigation.ui.theme.KiweyLearnsNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KiweyLearnsNavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "firstScreen", modifier = modifier){
        composable("firstScreen"){
            FirstScreen { name ->
                navController.navigate("secondScreen/$name")
            }
        }
        composable("secondScreen/{name}"){
            val temp: String = it.arguments?.getString("name").toString()
            val name =  if (temp== "") "No Name" else temp
            SecondScreen(name = name) {
                navController.navigate("firstScreen")
            }
        }
    }
}