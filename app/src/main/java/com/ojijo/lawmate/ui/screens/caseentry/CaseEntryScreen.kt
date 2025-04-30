package com.ojijo.lawmate.ui.screens.caseentry

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ojijo.lawmate.data.LawDatabaseHelper
import com.ojijo.lawmate.navigation.ROUT_CASE_LIST

@Composable
fun CaseEntryScreen(context: Context, navController: NavController) {
    val databaseHelper = LawDatabaseHelper(context)
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var courtDate by remember { mutableStateOf(TextFieldValue("")) }
    var status by remember { mutableStateOf(TextFieldValue("")) }
    var isDataSaved by remember { mutableStateOf(false) }

    // 🌈 Gradient background for a fresh, modern look
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFe0f7fa), Color.White)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Case Entry",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color(0xFF006064)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Case Description") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = courtDate,
                        onValueChange = { courtDate = it },
                        label = { Text("Court Date") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text("Case Status") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (description.text.isNotEmpty() && courtDate.text.isNotEmpty() && status.text.isNotEmpty()) {
                                databaseHelper.insertCase(description.text, courtDate.text, status.text)
                                isDataSaved = true
                                description = TextFieldValue("")
                                courtDate = TextFieldValue("")
                                status = TextFieldValue("")
                                Toast.makeText(context, "Case Saved Successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate(ROUT_CASE_LIST)
                            } else {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006064))
                    ) {
                        Text("Save Case", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { navController.navigate(ROUT_CASE_LIST) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back to Case List")
                    }

                    if (isDataSaved) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Case saved successfully!", color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaseEntryScreenPreview() {
    CaseEntryScreen(
        context = LocalContext.current,
        navController = rememberNavController()
    )
}
