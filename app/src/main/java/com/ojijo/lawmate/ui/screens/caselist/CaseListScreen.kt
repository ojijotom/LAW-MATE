package com.ojijo.lawmate.ui.screens.caselist

import android.content.Context
import android.widget.Toast
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ojijo.lawmate.data.LawDatabaseHelper
import com.ojijo.lawmate.navigation.ROUT_LAW_ENTRY

@Composable
fun CaseListScreen(context: Context, navController: NavHostController) {
    val databaseHelper = LawDatabaseHelper(context)
    val cases by remember { mutableStateOf(databaseHelper.getAllCases()) }

    // 🌈 Gradient background for visual appeal
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFe0f7fa), Color(0xFFffffff)) // Light blue to white gradient
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground) // Apply gradient background
            .padding(16.dp) // Padding inside the screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Case List",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color(0xFF006064))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ➕ Button to add new case (navigates to CaseEntry)
            Button(
                onClick = { navController.navigate(ROUT_LAW_ENTRY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064))
            ) {
                Text("Add New Case", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cases.isEmpty()) {
                Text("No cases found", color = Color.Gray)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(cases) { case ->
                        CaseListItem(case = case, context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun CaseListItem(case: Triple<String, String, String>, context: Context) {
    val description = case.first
    val courtDate = case.second
    val status = case.third

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Show the case details in a Toast on click
                Toast.makeText(
                    context,
                    "Description: $description\nDate: $courtDate\nStatus: $status",
                    Toast.LENGTH_SHORT
                ).show()
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Description: $description", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Court Date: $courtDate", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Status: $status", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaseListScreenPreview() {
    // This preview won't include navigation logic
    CaseListScreen(context = LocalContext.current, navController = rememberNavController())
}
