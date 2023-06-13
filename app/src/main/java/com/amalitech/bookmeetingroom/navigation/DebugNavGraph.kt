package com.amalitech.bookmeetingroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.bookmeetingroom.AppScaffold
import com.amalitech.bookmeetingroom.testComponents.DebugScreen
import com.amalitech.core_ui.components.drawer.BookMeetingRoomDrawer
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.onboarding.OnboardingScreen
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
        composable(route = NavigationTarget.ONBOARD.route) {
            OnboardingScreen(onNavigateToLogin = {})
        }
        composable(route = NavigationTarget.DASHBOARD.route) {
            AppScaffold(shouldShowOnboarding = false)
        }
        composable(route = NavigationTarget.DRAWER.route) {
            val appState = rememberBookMeetingRoomAppState()
            BookMeetingRoomTheme {
                BookMeetingRoomDrawer(
                    appState
                ) {}
            }
        }
        composable(route = NavigationTarget.CARD.route){

        }
    }
}