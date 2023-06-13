package com.amalitech.bookmeetingroom

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.onboarding.forgot_password.ForgotPasswordScreen
import com.amalitech.onboarding.login.LoginScreen
import com.amalitech.onboarding.reset_password.ResetPasswordScreen
import com.amalitech.onboarding.splash_screen.SplashScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationTarget.DEBUG.route
    ) {
        composable(route = NavigationTarget.DEBUG.route) {
            DebugScreen {
                navController.navigate(it.route)
            }
        }
        composable(route = NavigationTarget.LOGIN.route) {
            LoginScreen(
                onNavigateToHome = {},
                onNavigateToForgotPassword = {},
                onNavigateToSignUp = {}
            )
        }
        composable(route = NavigationTarget.FORGOT.route) {
            ForgotPasswordScreen(
                onNavigateToLogin = {},
                onNavigateToReset = {}
            )
        }
        composable(route = NavigationTarget.RESET.route) {
            ResetPasswordScreen(onNavigateToLogin = {})
        }
        composable(route = NavigationTarget.SPLASH.route) {
            SplashScreen(onNavigate = {})
        }
        composable(route = NavigationTarget.SPLASH.route) {
            SplashScreen(onNavigate = {})
        }
    }
}