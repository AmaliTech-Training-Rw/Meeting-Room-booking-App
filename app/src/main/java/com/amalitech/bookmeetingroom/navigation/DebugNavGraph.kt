package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.admin.DashboardCardItem
import com.amalitech.admin.components.DashBoardCard
import com.amalitech.admin.room.AddRoomScreen
import com.amalitech.booking.BookingScreen
import com.amalitech.bookmeetingroom.AppScaffold
import com.amalitech.bookmeetingroom.testComponents.DebugScreen
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.drawer.BookMeetingRoomDrawer
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.home.calendar.CalendarScreen
import com.amalitech.onboarding.OnboardingScreen
import com.amalitech.onboarding.forgot_password.ForgotPasswordScreen
import com.amalitech.onboarding.login.LoginScreen
import com.amalitech.onboarding.reset_password.ResetPasswordScreen
import com.amalitech.onboarding.signup.SignupScreen
import com.amalitech.onboarding.splash_screen.SplashScreen
import com.amalitech.rooms.book_room.BookRoomScreen
import com.amalitech.user.UserScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    Scaffold() {
        NavHost(
            navController = navController,
            startDestination = NavigationTarget.DEBUG.route,
            modifier = Modifier.padding(it)
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
            composable(route = NavigationTarget.DRAWER.route) {
                val appState = rememberBookMeetingRoomAppState()
                BookMeetingRoomTheme {
                    BookMeetingRoomDrawer(
                        appState = appState,
                        onClick = {}
                    )
                }
            }
            composable(route = NavigationTarget.CARD.route) {
                DashBoardCard(
                    DashboardCardItem(
                        label = "Users",
                        iconId = R.drawable.baseline_mail_outline_24,
                        count = 5
                    )
                )
            }

            composable(route = NavigationTarget.DASHBOARD.route) {
                AppScaffold(shouldShowOnboarding = false)
            }

            composable(route = NavigationTarget.USER.route) {
                UserScreen()
            }

            composable(route = NavigationTarget.APPBAR.route) {
                val appState = rememberBookMeetingRoomAppState()
                var query by rememberSaveable {
                    mutableStateOf("")
                }
                var isSearchTextFieldVisible by rememberSaveable {
                    mutableStateOf(false)
                }
                BookMeetingRoomDrawer(
                    appState = appState,
                    onClick = {
                        // TODO: cadet: please navigate to screen here, using navigate()
                    },
                    searchQuery = query,
                    onSearchQueryChange = { query = it },
                    {},
                    isSearchTextFieldVisible = isSearchTextFieldVisible,
                    onSearchTextFieldVisibilityChange = { isVisible ->
                        isSearchTextFieldVisible = isVisible

                        composable(route = NavigationTarget.ADDROOM.route) {
                            AddRoomScreen()
                        }

                        composable(route = NavigationTarget.APPBAR.route) {
                            val appState = rememberBookMeetingRoomAppState()
                            var query by rememberSaveable {
                                mutableStateOf("")
                            }
                            var isSearchTextFieldVisible by rememberSaveable {
                                mutableStateOf(false)
                            }
                            BookMeetingRoomDrawer(
                                appState = appState,
                                onClick = {
                                    // TODO: cadet: please navigate to screen here, using navigate()
                                },
                                searchQuery = query,
                                onSearchQueryChange = { query = it },
                                {},
                                isSearchTextFieldVisible = isSearchTextFieldVisible,
                                onSearchTextFieldVisibilityChange = { isVisible ->
                                    isSearchTextFieldVisible = isVisible
                                }
                            )
                        }

                        composable(route = NavigationTarget.ADDROOM.route) {
                            AddRoomScreen()
                        }

                        composable(route = NavigationTarget.CALENDAR.route) {
                            CalendarScreen()
                        }

                        composable(route = NavigationTarget.HOME.route) {
                            AppScaffold(shouldShowOnboarding = false)
                        }

                        composable(route = NavigationTarget.SIGNUP.route) { entry ->
                            SignupScreen(
                                onNavigateToLogin = { navController.navigate(NavigationTarget.LOGIN.route) },
                                navBackStackEntry = entry
                            )
                        }

                        composable(route = NavigationTarget.BOOK_ROOM.route) { entry ->
                            BookRoomScreen(navBackStackEntry = entry) {
                                navController.navigateUp()
                            }
                        }

                        composable(route = NavigationTarget.BOOKING.route) {
                            BookingScreen()
                        }
                    }
                )
            }
        }
    }
}