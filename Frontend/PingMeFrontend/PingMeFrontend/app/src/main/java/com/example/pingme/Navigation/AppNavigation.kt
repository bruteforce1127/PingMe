package com.example.pingme.Navigation

import android.os.Build
import android.net.Uri
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.auth0.android.jwt.JWT
import com.example.pingme.Auth.View.LoginScreen
import com.example.pingme.Auth.View.SignUpScreen
import com.example.pingme.Home.HomeScreen
import com.example.pingme.ReminderManagement.View.ReminderHistoryScreen
import com.example.pingme.ReminderManagement.View.ReminderScreen
import com.example.pingme.SplashScreen.SplashScreen
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current

    // Set up the navigation graph
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController, context)
        }
        composable(
            "signup"
        ) {
            SignUpScreen(
                goToLoginScreen = {
                    navController.navigate("login") }
            )
        }
        composable(
            "login"
        ) {
            LoginScreen(
                goToSignUpScreen = {
                    navController.navigate("signup")
                },
                goToHomeScreen = {username->
                    navController.navigate("homeScreen/$username")
                }
            )
        }
        composable(
            route = "homeScreen/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")?.let { Uri.decode(it) } ?: ""
            HomeScreen(
                goToSetReminderScreen = {
                    navController.navigate("ReminderScreen")
                },
                goToHistoryScreen = { username->
                    navController.navigate("history/$username")
                },
                goToInsightsScreen = { /*TODO*/ },
                goToNotificationScreen = {},
                goToSignUpScreen = {
                    navController.navigate("signup")
                },
                username = username
            )
        }
        composable(
            route = "ReminderScreen"
        ) {
            ReminderScreen(
                goToHomeScreen = { username->
                    navController.navigate("homeScreen/$username")
                }
            )
        }
        composable(
            route="history/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")?.let { Uri.decode(it) } ?: ""
                ReminderHistoryScreen(
                    username = username,
                    goToSignUpScreen = {
                        navController.navigate("homeScreen/$username")
                    }
                )
        }
    }
}


fun isTokenExpired(token: String): Boolean {
    return try {
        val jwt = JWT(token)
        jwt.expiresAt?.before(Date()) ?: true  // If there's no expiration date, treat as expired
    } catch (e: Exception) {
        // If token is invalid, consider it expired
        true
    }
}



