package com.amalitech.bookmeetingroom.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.home.HomeScreen
import com.amalitech.rooms.RoomListScreen
import com.amalitech.rooms.book_room.BookRoomScreen
import com.amalitech.user.UserScreen
import com.amalitech.user.profile.ProfileScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit,
    mainNavController: NavHostController
) {
    val isDrawerOpen by remember {
        mutableStateOf(appState.drawerState.isOpen)
    }
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
                    // TODO("NAVIGATE TO THE PROFILE SCREEN")
                },
                appState = appState,
                navigateUp = onFinishActivity
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
            TestScreen("This is ${NavigationItem.BookingRequests.title}", innerPadding)
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
                onUpdateProfileClick = { }
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
                    Log.d("Drawer", "$isDrawerOpen")
                }
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
