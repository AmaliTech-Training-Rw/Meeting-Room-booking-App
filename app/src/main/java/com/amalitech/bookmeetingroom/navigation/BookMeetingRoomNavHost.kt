package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.amalitech.admin.DashboardScreen
import com.amalitech.admin.room.AddRoomScreen
import com.amalitech.booking.history.BookingHistoryScreen
import com.amalitech.booking.requests.BookingRequestScreen
import com.amalitech.booking.requests.detail.BookingRequestDetailScreen
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage
import com.amalitech.home.HomeScreen
import com.amalitech.rooms.RoomListScreen
import com.amalitech.rooms.book_room.BookRoomScreen
import com.amalitech.user.UserScreen
import com.amalitech.user.profile.ProfileScreen
import com.amalitech.user.profile.ProfileViewModel
import com.amalitech.user.profile.update_profile.UpdateProfileScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit,
    mainNavController: NavHostController
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = NavigationItem.Home.route) {
            HomeScreen(
                onComposing = onComposing,
                navigateToProfileScreen = {
                    navigateToProfileScreen(appState)
                },
                appState = appState,
                navigateUp = { navigateToDashboard(appState) }
            ) {
                mainNavController.navigate("${Route.BOOK_ROOM_SCREEN}/$it")
            }
        }

        composable(
            route = "${Route.BOOK_ROOM_SCREEN}/{roomId}",
            arguments = listOf(navArgument("roomId") {
                type = NavType.StringType
            })
        ) {
            BookRoomScreen(
                appState = appState,
                navBackStackEntry = it,
                onComposing = onComposing,
                navigateBack = {
                    mainNavController.navigateUp()
                }
            ) {
                mainNavController.navigate(BottomNavItem.Home.route)
            }
        }


        composable(route = NavigationItem.BookingRequests.route) {
            BookingRequestScreen(
                appState = appState,
                navigateUp = { navigateToDashboard(appState) },
                onComposing = onComposing,
                navigateToProfileScreen = { navigateToProfileScreen(appState) }
            ) { booking ->
                appState.navController.navigate("${NavigationItem.BookingRequestDetail.route}/${booking.id}")
            }
        }

        composable(
            route = "${NavigationItem.BookingRequestDetail.route}/{booking}",
            arguments = listOf(navArgument("booking") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("booking")
            BookingRequestDetailScreen(
                id = id ?: "",
                onComposing = onComposing,
                onNavigateUp = { appState.navController.navigateUp() },
                showSnackBar = { SnackbarManager.showMessage(SnackbarMessage.StringSnackbar(it)) }
            )
        }

        composable(route = NavigationItem.Users.route) {
            UserScreen(
                appState = appState,
                onOpenDrawer = { openDrawer(scope, appState) },
                navigateToProfileScreen = {
                    navigateToProfileScreen(appState)
                },
                navigateUp = {
                    navigateToDashboard(appState)
                },
                onComposing = onComposing
            )
        }

        composable(route = NavigationItem.Profile.route) {
            ProfileScreen(
                appState = appState,
                navigateToProfileScreen = { },
                onNavigateBack = { navigateToDashboard(appState) },
                onComposing = onComposing,
                onUpdateProfileClick = { email ->
                    appState.navController.navigate("${NavigationItem.UpdateProfile.route}/$email")
                },
                onNavigateToLogin = {
                    mainNavController.navigateToLogin()
                },
                showSnackBar = {
                    scope.launch {
                        appState.snackbarHostState.showSnackbar(it)
                    }
                }
            ) { goToAdmin ->
                if (goToAdmin)
                    mainNavController.navigate(Route.DASHBOARD_SCREENS) {
                        popUpTo(Route.HOME_SCREENS) {
                            inclusive = true
                        }
                    }
                else {
                    mainNavController.navigate(Route.HOME_SCREENS) {
                        popUpTo(Route.DASHBOARD_SCREENS) {
                            inclusive = true
                        }
                    }
                    onComposing(AppBarState(hasTopBar = false))
                }
            }
        }

        composable(route = NavigationItem.Invitations.route) {
            TestScreen(
                "This is ${NavigationItem.Invitations.title}", innerPadding
            )
        }

        composable(route = NavigationItem.Invitations.route) {
            TestScreen(
                "This is ${NavigationItem.Invitations.title}", innerPadding
            )
        }

        composable(route = Route.ADD_ROOM_SCREEN) {
            AddRoomScreen(onComposing = onComposing) {
                appState.navController.navigateUp()
            }
        }

        composable(route = NavigationItem.Rooms.route) {
            RoomListScreen(
                onNavigateToAddRoom = {
                    appState.navController.navigate(Route.ADD_ROOM_SCREEN)
                },
                onComposing = {
                    onComposing(it)
                },
                onNavigateBack = {
                    navigateToDashboard(appState)
                },
                appState = appState,
                onOpenDrawer = { openDrawer(scope, appState) },
                navigateToProfileScreen = { navigateToProfileScreen(appState) }
            )
        }

        composable(route = NavigationItem.Dashboard.route) {
            DashboardScreen(
                appState = appState,
                onOpenDrawer = { openDrawer(scope, appState) },
                onComposing = onComposing,
                navigateUp = {
                    onFinishActivity()
                }
            ) {
                navigateToProfileScreen(appState)
            }
        }

        composable(route = NavigationItem.Logout.route) {
            val profileViewModel: ProfileViewModel = koinViewModel()
            profileViewModel.logout()
            LaunchedEffect(key1 = true) {
                mainNavController.navigateToLogin()
            }
        }

        composable(
            "${NavigationItem.UpdateProfile.route}/{email}",
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            UpdateProfileScreen(
                onComposing = onComposing,
                email = email,
                showSnackBar = {
                    scope.launch {
                        appState.snackbarHostState.showSnackbar(it)
                    }
                },
                onNavigateBack = { appState.navController.navigateUp() }
            )
        }

        composable(route = NavigationItem.BookingHistory.route) {
            BookingHistoryScreen(
                navigateUp = { navigateToDashboard(appState) },
                onComposing = onComposing,
                appState = appState
            ) {
                navigateToProfileScreen(appState)
            }
        }
    }
}

private fun navigateToDashboard(appState: BookMeetingRoomAppState) {
    appState.navController.navigate(NavigationItem.Dashboard.route) {
        popToDashboard()
    }
}

private fun navigateToProfileScreen(appState: BookMeetingRoomAppState) {
    appState.navController.navigate(NavigationItem.Profile.route) {
        popToDashboard()
    }
}

fun NavOptionsBuilder.popToDashboard() {
    popUpTo(NavigationItem.Dashboard.route) {
        inclusive = true
    }
    launchSingleTop
}

fun openDrawer(scope: CoroutineScope, appState: BookMeetingRoomAppState) {
    scope.launch {
        appState.drawerState.open()
    }
}

@Composable
fun TestScreen(
    text: String,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
