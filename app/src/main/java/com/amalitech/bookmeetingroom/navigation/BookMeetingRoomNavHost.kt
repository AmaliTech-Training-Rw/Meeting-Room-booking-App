package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState,
    mainNavController: NavHostController
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        composable(route = NavigationItem.Home.route) {
            TestScreen("This is ${NavigationItem.Home.title}", innerPadding)
        }

        composable(route = NavigationItem.BookingRequests.route) {
            TestScreen("This is ${NavigationItem.BookingRequests.title}", innerPadding)
        }

        composable(route = NavigationItem.Profile.route) {
            Column {
                TestScreen(
                    "This is ${NavigationItem.Profile.title}",
                    innerPadding,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        mainNavController.navigate(Route.HOME_SCREENS) {
                            popUpTo(Route.DASHBOARD_SCREENS) {
                                inclusive = true
                            }
                        }
                    }, modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text("Switch to normal user")
                }

            }
        }

        composable(route = NavigationItem.Invitations.route) {
            TestScreen(
                "This is ${NavigationItem.Invitations.title}", innerPadding
            )
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
