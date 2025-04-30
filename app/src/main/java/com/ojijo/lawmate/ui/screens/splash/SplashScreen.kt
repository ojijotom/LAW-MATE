package com.ojijo.lawmate.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ojijo.lawmate.R
import com.ojijo.lawmate.navigation.ROUT_REGISTER
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Set a delay of 3 seconds for the splash screen
    LaunchedEffect(key1 = true) {
        delay(3000)  // 3-second splash screen duration
        navController.navigate(ROUT_REGISTER) { // Navigate to the entry screen
            popUpTo("splash") { inclusive = true }
        }
    }

    // Splash Screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient( // Adding gradient background
                colors = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))
            )),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo image with a shadow effect for better visibility
            Image(
                painter = painterResource(id = R.drawable.logo), // Your logo resource
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .padding(16.dp)
                    .shadow(8.dp, shape = CircleShape) // Adding shadow effect to logo
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App name or tagline with stylish font
            Text(
                text = "LawMate",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White,  // White text for contrast on gradient background
                    shadow = Shadow(color = Color.Black, offset = Offset(2f, 2f), blurRadius = 4f)
                ),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Optional Progress Bar with customized color
            CircularProgressIndicator(
                color = Color.White,  // Set progress indicator color to white
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp) // Make the progress indicator larger
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    // Preview the SplashScreen layout
    SplashScreen(navController = rememberNavController())
}
