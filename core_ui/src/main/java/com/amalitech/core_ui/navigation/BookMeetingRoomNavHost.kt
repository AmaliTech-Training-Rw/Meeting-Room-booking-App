package com.amalitech.core_ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.BookMeetingRoomAppState

@Composable
fun BookMeetingRoomNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: BookMeetingRoomAppState
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        composable(route = NavigationItem.Home.route) {
            TestScreen("This is ${NavigationItem.Home.title}", appState, innerPadding)
        }

        composable(route = NavigationItem.BookingRequests.route) {
            TestScreen("This is ${NavigationItem.BookingRequests.title}", appState, innerPadding)
        }

        composable(route = NavigationItem.Profile.route) {
            TestScreen("This is ${NavigationItem.Profile.title}", appState, innerPadding)
        }

        composable(route = NavigationItem.Invitations.route) {
            TestScreen(
                "This is ${NavigationItem.Invitations.title}", appState, innerPadding)
        }
    }
}

@Composable
fun TestScreen(
    text: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

