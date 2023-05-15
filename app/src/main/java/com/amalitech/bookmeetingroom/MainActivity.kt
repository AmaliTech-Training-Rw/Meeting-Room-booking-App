package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amalitech.bookmeetingroom.authentication_presentation.forgot_password.ForgotPasswordScreen
import com.amalitech.bookmeetingroom.authentication_presentation.login.LoginScreen
import com.amalitech.bookmeetingroom.authentication_presentation.reset_password.ResetPasswordScreen
import com.amalitech.bookmeetingroom.navigation.Route
import com.amalitech.bookmeetingroom.onboarding_presentation.OnboardingScreen
import com.amalitech.bookmeetingroom.onboarding_presentation.SplashScreen
import com.amalitech.bookmeetingroom.ui.theme.BookMeetingRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                val navController = rememberNavController()
                var currentOnboardIndex by rememberSaveable {
                    mutableStateOf(0)
                }

                NavHost(navController = navController, startDestination = Route.ONBOARDING) {
                    composable(Route.ONBOARDING) {
                        OnboardingScreen(
                            onGetStartedClick = {
                                navController.navigate(Route.SPLASH)
                            },
                            onSwipe = {
                                currentOnboardIndex = it
                            },
                            currentIndex = currentOnboardIndex
                        )
                    }
                    composable(Route.SPLASH) {
                        SplashScreen(onNavigate = { navController.navigate(Route.LOGIN) })
                    }
                    composable(Route.RESET_PASSWORD) {
                        ResetPasswordScreen(onNavigateToHome = {
                            // TODO("update navigation to navigate to the
                            //  corresponding screens when they are created")
                        })
                    }
                    composable(Route.FORGOT_PASSWORD) {
                        ForgotPasswordScreen(
                            onNavigateToHome = {
                                // TODO("update navigation to navigate to the
                                //  corresponding screens when they are created")
                            },
                            onNavigateToLogin = {
                                navController.navigate(Route.LOGIN)
                            }
                        )
                    }
                    composable(Route.LOGIN) {
                        LoginScreen(
                            // TODO("update navigation to navigate to the
                            //  corresponding screens when they are created")
                            onNavigateToHome = {

                            },
                            onNavigateToSignUp = {

                            },
                            onNavigateToForgotPassword = {
                                navController.navigate(Route.FORGOT_PASSWORD)
                            },
                            onNavigateUp = {
                                navController.navigate(Route.ONBOARDING) {
                                    popUpTo(Route.ONBOARDING) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}
