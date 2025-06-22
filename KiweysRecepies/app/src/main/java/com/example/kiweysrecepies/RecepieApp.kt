package com.example.kiweysrecepies

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecepieApp(modifier: Modifier, navController: NavHostController){
    val recepieViewModel: MainViewModel = viewModel ()
    val viewState by recepieViewModel.categoriesState

    NavHost(navController = navController, startDestination = Screen.RecepieScreen.route) {
        composable(route = Screen.RecepieScreen.route) {
            RecepieScreen(
                viewState = viewState,
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat", it )
                    navController.navigate(Screen.DetailScreen.route)
                }
            )
        }
        composable(route = Screen.DetailScreen.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat") ?: Category("","","","")
            CategoryDetailsScreen(category = category)
        }
    }
}