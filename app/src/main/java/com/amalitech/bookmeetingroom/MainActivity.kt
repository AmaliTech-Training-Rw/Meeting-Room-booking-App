package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.amalitech.bookmeetingroom.navigation.Route
import com.amalitech.core_ui.bottom_navigation.BottomNavBar
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.onboarding.OnboardingScreen
import com.amalitech.onboarding.forgot_password.ForgotPasswordScreen
import com.amalitech.onboarding.forgot_password.ForgotPasswordViewModel
import com.amalitech.onboarding.login.LoginScreen
import com.amalitech.onboarding.login.LoginViewModel
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import com.amalitech.onboarding.reset_password.ResetPasswordScreen
import com.amalitech.onboarding.reset_password.ResetPasswordViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val sharedPref: OnboardingSharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                val shouldShowOnboarding = sharedPref.loadShouldShowOnboarding()
                AppNavigation(shouldShowOnboarding)
            }
        }
    }
}

@Composable
fun AppNavigation(shouldShowOnboarding: Boolean) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            if (BottomNavItem.createItems().any { it.route == currentRoute?.route }) {
                BottomNavBar(currentDestination = currentRoute, onClick = {
                    navController.navigate(it.route) {
                        popUpTo(
                            BottomNavItem.Home.route
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.ONBOARDING_SCREENS,
            modifier = Modifier.padding(paddingValues)
        ) {
            onboardingGraph(navController, shouldShowOnboarding)
            mainNavGraph()
            dashboardNavGraph()
        }
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

        composable(Route.SIGNUP) {
            // TODO (ADD SIGN UP SCREEN COMPOSABLE HERE)
            Text("Sign up screen")
        }

        composable(Route.SPLASH) {
            // TODO (ADD SPLASH SCREEN COMPOSABLE HERE)
            Text("Splash Screen")
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

fun NavGraphBuilder.mainNavGraph() {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = Route.HOME_SCREENS
    ) {
        composable(BottomNavItem.Home.route) {
            // TODO (ADD HOME SCREEN COMPOSABLE HERE)
            Text("Home Screen")
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
            // TODO (ADD BOOKINGS SCREEN COMPOSABLE HERE)
            Text("Bookings screen")
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookMeetingRoomTheme {
        Greeting("Android")
    }
}
