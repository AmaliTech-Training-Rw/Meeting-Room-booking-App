package com.amalitech.bookmeetingroom.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.amalitech.user.profile.update_profile.UpdateProfileScreen
import com.tradeoases.invite.InviteScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
    val scope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = Route.ONBOARDING_SCREENS,
        modifier = modifier
    ) {
        onboardingGraph(navController, shouldShowOnboarding, snackbarHostState, onComposing)
        mainNavGraph(navController, snackbarHostState, onComposing, onFinishActivity, scope)
        dashboardNavGraph(
            navController,
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

        val uriSecured = "https://api.meeting-room.amalitech-dev.net"
        val uriUnsecured = "http://api.meeting-room.amalitech-dev.net"
        composable(
            "${Route.SIGNUP}?token={token}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$uriSecured/user/invite/api/{token}"
                },
                navDeepLink {
                    uriPattern =
                        "$uriUnsecured/user/invite/api/{token}"
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

        composable(
            "${Route.RESET_PASSWORD}?token={token}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$uriSecured/password/reset/api/{token}"
                },
                navDeepLink {
                    uriPattern =
                        "$uriUnsecured/password/reset/api/{token}"
                }
            )
        ) { navBackStackEntry ->
            val viewModel: ResetPasswordViewModel =
                navBackStackEntry.sharedViewModel(navController = navController)
            val token = navBackStackEntry.arguments?.getString(NavArguments.token) ?: ""
            ResetPasswordScreen(
                onComposing = onComposing,
                snackbarHostState = snackbarHostState,
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Route.LOGIN) {
                        launchSingleTop = true
                        popUpTo(Route.LOGIN)
                    }
                }, token = token)
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit,
    scope: CoroutineScope
) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = Route.HOME_SCREENS
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                showSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onComposing = onComposing,
                navigateToProfileScreen = {
                    navigateToProfileScreen(navController)
                },
                navigateUp = onFinishActivity
            ) {
                navController.navigate("${Route.BOOK_ROOM_SCREEN}/$it")
            }
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
                    onComposing(AppBarState(hasTopBar = false))
                }
            ) {
                navController.navigate(BottomNavItem.Home.route)
            }
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(
                showSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onNavigateToLogin = {
                    navController.navigateToLogin()
                },
                navigateToProfileScreen = {},
                onNavigateBack = {
                    navController.navigate(BottomNavItem.Home.route) {
                        popUpTo(BottomNavItem.Home.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onComposing = onComposing,
                onUpdateProfileClick = { email ->
                    navController.navigate("${Route.UPDATE_PROFILE}/$email")
                }
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
            InviteScreen(
                navigateToProfileScreen = { navigateToProfileScreen(navController) },
                onComposing = onComposing,
                showSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }
        composable(BottomNavItem.Bookings.route) {
            BookingScreen(
                navigateToProfileScreen = { navigateToProfileScreen(navController) },
                onComposing = onComposing,
                showSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }
        composable(
            "${Route.UPDATE_PROFILE}/{email}",
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            UpdateProfileScreen(
                email = email,
                showSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onNavigateBack = { navController.navigateUp() },
                onComposing = onComposing
            )
        }
    }
}

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
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
                    if (appState.navController.currentDestination?.route != it.route)
                        appState.navController.navigate(it.route) {
                            popToDashboard()
                        }
                }
            ) {
                BookMeetingRoomApp(
                    appState = appState,
                    mainNavController = navController,
                    onFinishActivity = onFinishActivity
                )
            }
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
    popUpTo(
        BottomNavItem.Home.route
    ) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

fun NavHostController.navigateToLogin() {
    navigate(Route.ONBOARDING_SCREENS) {
        popUpTo(Route.ONBOARDING_SCREENS) {
            inclusive = true
        }
        launchSingleTop = true
    }
    navigate(Route.LOGIN) {
        popUpTo(Route.DASHBOARD_SCREENS) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
