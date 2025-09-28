package com.myapp.voicehealth.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapp.voicehealth.ui.screens.AskAIScreen
import com.myapp.voicehealth.ui.screens.DoctorDetailsScreen
import com.myapp.voicehealth.ui.screens.DoctorSuggestionScreen
import com.myapp.voicehealth.ui.screens.ForgotPasswordScreen
import com.myapp.voicehealth.ui.screens.HealthHistoryScreen
import com.myapp.voicehealth.ui.screens.HomeScreen
import com.myapp.voicehealth.ui.screens.authScreens.LoginScreen
import com.myapp.voicehealth.ui.screens.NearbyDoctorsScreen
import com.myapp.voicehealth.ui.screens.OtpVerificationScreen
import com.myapp.voicehealth.ui.screens.ScanReportScreen
import com.myapp.voicehealth.ui.screens.authScreens.SignUpScreen
import com.myapp.voicehealth.ui.screens.SplashScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") { SplashScreen(navController) }
        composable("login_screen") { LoginScreen(navController) }
        composable("home_screen") { HomeScreen(navController) }
        composable("register_screen") { SignUpScreen(navController) }
        composable("forgot_password_screen") { ForgotPasswordScreen(navController) }
        composable("otp_verification_screen") { OtpVerificationScreen(navController) }
        composable("doctor_suggestion") { DoctorSuggestionScreen(navController) }
        composable(
            "doctor_details/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: -1
            DoctorDetailsScreen(navController = navController, doctorId = doctorId)
        }
        composable("ask_ai_screen") { AskAIScreen(navController) }
        composable("scan_report_screen") { ScanReportScreen(navController) }
        composable("health_history_screen") { HealthHistoryScreen(navController) }
        composable("nearby_doctors_screen") { NearbyDoctorsScreen(navController) }
    }
}


