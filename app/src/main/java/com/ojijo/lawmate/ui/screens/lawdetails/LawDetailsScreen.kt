package com.ojijo.lawmate.ui.screens.lawdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ojijo.lawmate.navigation.ROUT_CASE_ENTRY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LawDetailsScreen(
    title: String,
    content: String,
    navController: NavController
) {
    // 🌈 Beautiful gradient background
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFb2ebf2), Color(0xFFe0f7fa), Color.White)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Law Details",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF006064)
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
                .padding(padding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color(0xFF004D40),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Content",
                    style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 22.sp,
                        color = Color.DarkGray
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate(ROUT_CASE_ENTRY) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064))
                ) {
                    Text("View Related Cases", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LawDetailsScreenPreview() {
    LawDetailsScreen(
        title = "Constitution Act",
        content = "This law defines the structure of the judiciary, the separation of powers, and outlines the core principles upon which justice is administered...",
        navController = rememberNavController()
    )
}
