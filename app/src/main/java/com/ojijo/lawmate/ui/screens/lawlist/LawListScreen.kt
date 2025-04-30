package com.ojijo.lawmate.ui.screens.lawlist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ojijo.lawmate.data.LawDatabaseHelper

@Composable
fun LawListScreen(navController: NavController, context: Context) {
    val databaseHelper = LawDatabaseHelper(context)
    val laws by remember { mutableStateOf(databaseHelper.getAllLaws()) }

    // 🌈 Beautiful Gradient Background
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFb2ebf2), Color(0xFFe0f7fa), Color.White)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Laws List",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color(0xFF004d40),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (laws.isEmpty()) {
                Text("No laws found", color = Color.Gray)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(laws) { law ->
                        LawListItem(law = law, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun LawListItem(law: Pair<String, String>, navController: NavController) {
    val title = law.first
    val content = law.second

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("law_details/$title/$content")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF006064)),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content.take(120) + "...",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LawListScreenPreview() {
    val context = LocalContext.current
    val navController = rememberNavController()
    LawListScreen(navController = navController, context = context)
}
