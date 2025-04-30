package com.ojijo.lawmate.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ojijo.lawmate.data.UserDatabase
import com.ojijo.lawmate.repository.UserRepository
import com.ojijo.lawmate.ui.screens.auth.LoginScreen
import com.ojijo.lawmate.ui.screens.auth.RegisterScreen
import com.ojijo.lawmate.ui.screens.caseentry.CaseEntryScreen
import com.ojijo.lawmate.ui.screens.caselist.CaseListScreen
import com.ojijo.lawmate.ui.screens.lawdetails.LawDetailsScreen
import com.ojijo.lawmate.ui.screens.lawentry.LawEntryScreen
import com.ojijo.lawmate.ui.screens.lawlist.LawListScreen
import com.ojijo.lawmate.ui.screens.splash.SplashScreen
import com.ojijo.lawmate.viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    startDestination: String = ROUT_SPLASH
) {
    // Initialize Room Database and Repository for Authentication
    val appDatabase = UserDatabase.getDatabase(context)
    val authRepository = UserRepository(appDatabase.userDao())
    val authViewModel = AuthViewModel(authRepository)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash screen
        composable(ROUT_SPLASH) {
            SplashScreen(navController = navController)
        }

        // Authentication
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_LAW_ENTRY) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

        // Law entry screen
        composable(ROUT_LAW_ENTRY) {
            LawEntryScreen(context = context, navController = navController)
        }

        // Law list screen
        composable(ROUT_LAW_LIST) {
            LawListScreen(navController = navController, context = context)
        }

        // Case list screen
        composable(ROUT_CASE_LIST) {
            CaseListScreen(context = context, navController = navController)
        }

        // Case entry screen
        composable(ROUT_CASE_ENTRY) {
            CaseEntryScreen(context = context, navController = navController)
        }

        // Law details screen
        composable(
            route = "$ROUT_LAW_DETAILS/{title}/{content}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("content") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val content = backStackEntry.arguments?.getString("content") ?: ""
            LawDetailsScreen(
                title = title,
                content = content,
                navController = navController
            )
        }
    }
}
