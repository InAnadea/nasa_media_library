package com.example.nasaimageandvideolibrary

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nasaimageandvideolibrary.ui.theme.NASAImageAndVideoLibraryTheme

@Composable
fun App() {
    NASAImageAndVideoLibraryTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.FIRST) {
            composable(Routes.FIRST) {
                FirstScreen(onNavigateToSecond = {
                    navController.navigate(Routes.SECOND)
                })
            }
            composable(Routes.SECOND) { SecondScreen() }
        }
    }
}

object Routes {
    const val FIRST = "first"
    const val SECOND = "second"
}
