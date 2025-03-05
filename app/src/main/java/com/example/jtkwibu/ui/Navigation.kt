package com.example.jtkwibu.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Search : Screen("search")
    object Bookmark : Screen("bookmark")
    object Profile : Screen("profile")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) { HomeScreen(onAnimeClick = {  }) }
        composable(Screen.Search.route) {
            SearchScreen(onAnimeClick = {  })
        }
        composable(Screen.Bookmark.route) { BookmarkScreen(onAnimeClick = {  }) }
        composable(Screen.Profile.route) {
            ProfileScreen(context = context)  // Kirim context ke ProfileScreen
        }
    }
}
