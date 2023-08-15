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
import com.amalitech.booking.requests.BookingRequestScreen
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.user.UserScreen
import com.amalitech.user.profile.ProfileScreen
import com.tradeoases.invite.InviteScreen

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState,
    mainNavController: NavHostController,
    setFabOnClick: ((() -> Unit)?) -> Unit
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = NavigationItem.Home.route) {
            TestScreen("This is ${NavigationItem.Home.title}", innerPadding)
        }

        composable(route = NavigationItem.BookingRequests.route) {
            BookingRequestScreen() { booking ->
                appState.navController.navigate("${NavigationItem.BookingRequestDetail.route}/${booking.id}")
            }
        }

        composable(
            route = "${NavigationItem.BookingRequestDetail.route}/{booking}",
            arguments = listOf(navArgument("booking") { type = NavType.StringType})
        ) { backStackEntry ->

            Text("coming soon\nbooking id: ${backStackEntry.arguments?.getString("booking")}")
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
            InviteScreen()
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

