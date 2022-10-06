package com.example.nasaimageandvideolibrary

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nasaimageandvideolibrary.ui.theme.NASAImageAndVideoLibraryTheme

@Composable
fun App() {
    NASAImageAndVideoLibraryTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.SEARCH) {
            composable(Routes.SEARCH) { SearchScreen(navController) }
            composable(
                "${Routes.MEDIA}/{${Routes.MEDIA_PARAM}}",
                arguments = listOf(navArgument(Routes.MEDIA_PARAM) { type = NavType.StringType })
            ) { backStackEntry ->
                MediaScreen(
                    backStackEntry.arguments?.getString(Routes.MEDIA_PARAM)!!,
                    navController
                )
            }
        }
    }
}

object Routes {
    const val SEARCH = "search"
    const val MEDIA = "media"
    const val MEDIA_PARAM = "nasaId"
}
