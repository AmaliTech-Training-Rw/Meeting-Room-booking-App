package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
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

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState,
    mainNavController: NavHostController,
    onComposing: (AppBarState) -> Unit,
    onFinishActivity: () -> Unit,
    mainNavController: NavHostController,
    setFabOnClick: ((() -> Unit)?) -> Unit
) {
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
                innerPadding = innerPadding,
                setFabOnClick = setFabOnClick
            )
        }

        composable(route = NavigationItem.Profile.route) {
            ProfileScreen(onUpdateProfileClick = { }, onToggleButtonClick = { goToAdmin ->
                if (goToAdmin)
                    mainNavController.navigate(Route.DASHBOARD_SCREENS) {
                        popUpTo(Route.HOME_SCREENS) {
                            inclusive = true
                        }
                    }
                else
                    mainNavController.navigate(Route.HOME_SCREENS) {
                        popUpTo(Route.DASHBOARD_SCREENS) {
                            inclusive = true
                        }
                    }
            })
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
            AddRoomScreen()
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
                    appState.navController.navigateUp()
                }
            )
        }

        composable(route = NavigationItem.Dashboard.route) {
            DashboardScreen()
        }
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
