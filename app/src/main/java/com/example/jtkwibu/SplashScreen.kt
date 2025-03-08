package com.example.jtkwibu


import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.jtkwibu.ui.Screen

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    // Lottie Animation State
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 3,
        isPlaying = true
    )

    // Navigate after animation completes
    LaunchedEffect(progress) {
        if (progress == 1f) {
            navController.navigate(Screen.Onboarding.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // UI Layout
    Box(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(250.dp)
        )
    }
}