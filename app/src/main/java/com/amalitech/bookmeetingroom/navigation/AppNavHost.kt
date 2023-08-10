package com.amalitech.bookmeetingroom.navigation

import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.amalitech.booking.BookingScreen
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.drawer.BookMeetingRoomDrawer
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.home.HomeScreen
import com.amalitech.onboarding.OnboardingScreen
import com.amalitech.onboarding.forgot_password.ForgotPasswordScreen
import com.amalitech.onboarding.forgot_password.ForgotPasswordViewModel
import com.amalitech.onboarding.login.LoginScreen
import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.reset_password.ResetPasswordScreen
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import com.amalitech.onboarding.signup.NavArguments
import com.amalitech.onboarding.signup.SignupScreen
import com.amalitech.onboarding.splash_screen.SplashScreen
import com.amalitech.rooms.book_room.BookRoomScreen
import com.amalitech.user.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    shouldShowOnboarding: Boolean,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit
) {
    var isGestureEnabled by rememberSaveable {
        mutableStateOf(true)
    }
    NavHost(
        navController = navController,
        startDestination = Route.ONBOARDING_SCREENS,
        modifier = modifier
    ) {
        onboardingGraph(navController, shouldShowOnboarding, snackbarHostState, onComposing)
        mainNavGraph(navController, snackbarHostState, onComposing, onFinishActivity)
        dashboardNavGraph(
            navController,
            isGestureEnabled,
            { isGestureEnabled = it },
            onFinishActivity
        )
    }
}

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    shouldShowOnboarding: Boolean,
    snackbarHostState: SnackbarHostState,
    onComposing: (AppBarState) -> Unit
) {
    navigation(
        startDestination =
        if (shouldShowOnboarding)
            Route.ONBOARDING else
            Route.SPLASH,
        route = Route.ONBOARDING_SCREENS
    ) {
        composable(Route.ONBOARDING) {
            OnboardingScreen(onComposing = onComposing) {
                navController.navigate(Route.LOGIN)
            }
        }

        composable(Route.LOGIN) {
            val viewModel: LoginViewModel = it.sharedViewModel(navController = navController)
            LoginScreen(
                onComposing = onComposing,
                onNavigateToHome = { isAdmin ->
                    if (isAdmin) {
                        navController.navigate(Route.DASHBOARD_SCREENS) {
                            popUpTo(Route.ONBOARDING_SCREENS) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Route.HOME_SCREENS) {
                            popUpTo(Route.ONBOARDING_SCREENS) {
                                inclusive = true
                            }
                        }
                    }
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Route.FORGOT_PASSWORD) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Route.SIGNUP) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                },
                viewModel = viewModel,
                snackBarHostState = snackbarHostState
            )
        }

        composable(Route.FORGOT_PASSWORD) {
            val viewModel: ForgotPasswordViewModel =
                it.sharedViewModel(navController = navController)
            ForgotPasswordScreen(
                onComposing = onComposing,
                onNavigateToLogin = {
                    navController.navigate(Route.LOGIN) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                },
                onNavigateToReset = {
                    navController.navigate(Route.RESET_PASSWORD) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                },
                viewModel = viewModel,
                snackbarHostState = snackbarHostState
            )
        }

        composable(
            Route.SIGNUP,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern =
                        "http://www.example.com/{${NavArguments.organizationName}}/{${NavArguments.email}}/{${NavArguments.typeOfOrganization}}/{${NavArguments.location}}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument(NavArguments.organizationName) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(NavArguments.email) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(NavArguments.typeOfOrganization) {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(NavArguments.location) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            SignupScreen(
                onComposing = onComposing,
                onNavigateToLogin = {
                    navController.navigate(Route.LOGIN) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                },
                navBackStackEntry = entry,
                snackbarHostState = snackbarHostState
            )
        }

        composable(Route.SPLASH) {
            SplashScreen(
                onComposing = onComposing,
                onNavigate = { isUserAdmin ->
                    if (isUserAdmin) {
                        navController.navigate(Route.DASHBOARD_SCREENS) {
                            popUpTo(Route.SPLASH) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Route.HOME_SCREENS) {
                            popUpTo(Route.SPLASH) {
                                inclusive = true
                            }
                        }
                    }
                })
        }

        composable(Route.RESET_PASSWORD) {
            val viewModel: ResetPasswordViewModel =
                it.sharedViewModel(navController = navController)
            ResetPasswordScreen(
                onComposing = onComposing,
                snackbarHostState = snackbarHostState,
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Route.LOGIN) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                })
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit
) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = Route.HOME_SCREENS
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                onComposing = onComposing,
                navigateToProfileScreen = {
                    navigateToProfileScreen(navController)
                },
                navigateToBookRoomScreen = {
                    navController.navigate("${Route.BOOK_ROOM_SCREEN}/$it")
                },
                navigateUp = onFinishActivity
            )
        }

        composable(
            route = "${Route.BOOK_ROOM_SCREEN}/{roomId}",
            arguments = listOf(navArgument("roomId") {
                type = NavType.StringType
            })
        ) {
            BookRoomScreen(
                snackbarHostState = snackbarHostState,
                navBackStackEntry = it,
                onComposing = onComposing,
                navigateBack = {
                    navController.navigateUp()
                }
            ) {
                navController.navigate(BottomNavItem.Home.route)
            }
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(
                onUpdateProfileClick = {
                    /*TODO("Navigate to Update profile screen")*/
                },
                onComposing = onComposing,
                onNavigateBack = {
                    navController.navigate(BottomNavItem.Home.route) {
                        popUpTo(BottomNavItem.Home.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                navigateToProfileScreen = {},
                onFinishActivity = onFinishActivity
            ) { goToAdmin ->
                if (goToAdmin) {
                    navController.navigate(Route.DASHBOARD_SCREENS) {
                        popUpTo(Route.HOME_SCREENS) {
                            inclusive = true
                        }
                    }
                    onComposing(AppBarState(hasTopBar = false))
                } else
                    navController.navigate(Route.HOME_SCREENS) {
                        popUpTo(Route.DASHBOARD_SCREENS) {
                            inclusive = true
                        }
                    }
            }
        }
        composable(BottomNavItem.Invitations.route) {
            BookRoomScreen(
                navBackStackEntry = it,
                navigateBack = {
                    navController.navigateUp()
                },
                onComposing = onComposing
            ) {}
        }
        composable(BottomNavItem.Bookings.route) {
            BookingScreen()
        }
    }
}

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    isGestureEnabled: Boolean,
    onGestureStateChange: (isEnabled: Boolean) -> Unit,
    onFinishActivity: () -> Unit
) {
    navigation(
        startDestination = Route.ADMIN_DASHBOARD,
        route = Route.DASHBOARD_SCREENS
    ) {
        composable(Route.ADMIN_DASHBOARD) {
            val appState = rememberBookMeetingRoomAppState()
            BookMeetingRoomDrawer(
                appState = appState,
                onClick = {
                    appState.navController.navigate(it.route) {
                        popToDashboard()
                    }
                },
                content = {
                    BookMeetingRoomApp(
                        appState = appState,
                        mainNavController = navController,
                        onFinishActivity = onFinishActivity,
                        onGestureStateChange = onGestureStateChange
                    )
                },
                isGestureEnabled = isGestureEnabled
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

private fun navigateToProfileScreen(navController: NavHostController) {
    navController.navigate(BottomNavItem.Profile.route) {
        popToHome()
    }
}

private fun NavOptionsBuilder.popToHome() {
    popUpTo(BottomNavItem.Profile.route) {
        inclusive = true
    }
    launchSingleTop
}
