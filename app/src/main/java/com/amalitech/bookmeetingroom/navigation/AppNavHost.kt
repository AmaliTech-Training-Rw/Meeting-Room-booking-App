package com.amalitech.bookmeetingroom.navigation

import android.content.Intent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.amalitech.booking.BookingScreen
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.home.book_room.BookRoomScreen
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    shouldShowOnboarding: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.ONBOARDING_SCREENS,
        modifier = modifier
    ) {
        onboardingGraph(navController, shouldShowOnboarding)
        mainNavGraph(navController)
        dashboardNavGraph()
    }
}

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    shouldShowOnboarding: Boolean
) {
    navigation(
        startDestination =
        if (shouldShowOnboarding)
            Route.ONBOARDING else
            Route.SPLASH,
        route = Route.ONBOARDING_SCREENS
    ) {
        composable(Route.ONBOARDING) {
            OnboardingScreen {
                navController.navigate(Route.LOGIN)
            }
        }

        composable(Route.LOGIN) {
            val viewModel: LoginViewModel = it.sharedViewModel(navController = navController)
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Route.HOME_SCREENS) {
                        popUpTo(Route.ONBOARDING_SCREENS) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToForgotPassword = { navController.navigate(Route.FORGOT_PASSWORD) },
                onNavigateToSignUp = { navController.navigate(Route.SIGNUP) },
                viewModel = viewModel
            )
        }

        composable(Route.FORGOT_PASSWORD) {
            val viewModel: ForgotPasswordViewModel =
                it.sharedViewModel(navController = navController)
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.navigate(Route.LOGIN) },
                onNavigateToReset = { navController.navigate(Route.RESET_PASSWORD) },
                viewModel = viewModel
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
                onNavigateToLogin = { navController.navigate(Route.LOGIN) },
                navBackStackEntry = entry
            )
        }

        composable(Route.SPLASH) {
            SplashScreen(onNavigate = { isUserAdmin ->
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
                viewModel = viewModel,
                onNavigateToLogin = { navController.navigate(Route.LOGIN) })
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = Route.HOME_SCREENS
    ) {
        composable(BottomNavItem.Home.route) {
            BookRoomScreen(navBackStackEntry = it) {
                navController.navigateUp()
            }
        }
        composable(BottomNavItem.Profile.route) {
            // TODO (ADD PROFILE SCREEN COMPOSABLE HERE)
            Text("Profile Screen")
        }
        composable(BottomNavItem.Invitations.route) {
            // TODO (ADD INVITATIONS SCREEN COMPOSABLE HERE)
            Text("Invitations screen")
        }
        composable(BottomNavItem.Bookings.route) {
            BookingScreen()
        }
    }
}

fun NavGraphBuilder.dashboardNavGraph() {
    navigation(
        startDestination = Route.ADMIN_DASHBOARD,
        route = Route.DASHBOARD_SCREENS
    ) {
        composable(Route.ADMIN_DASHBOARD) {
            // TODO (ADD DASHBOARD SCREEN COMPOSABLE HERE)
            Text("Admin dashboard")
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
