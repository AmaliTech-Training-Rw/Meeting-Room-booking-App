package com.amalitech.core_ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amalitech.core_ui.state.BookMeetingRoomAppState

const val homeNavigationRoute = "home_route"
const val profileNavigationRoute = "profile_route"
const val myBookingsNavigationRoute = "my_bookings_route"
const val invitationsNavigationRoute = "invitations_route"

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
        HomeGraph(
            appState,
            innerPadding
        )

        ProfileGraph(
            appState,
            innerPadding
        )

        MyBookingsGraph(
            appState,
            innerPadding
        )

        InvitationsGraph(
            appState,
            innerPadding
        )
    }
}

fun NavGraphBuilder.HomeGraph(
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    composable(route = homeNavigationRoute) {
        TestScreen("This is home", appState, innerPadding)
    }
}

fun NavGraphBuilder.ProfileGraph(
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    composable(route = profileNavigationRoute) {
        TestScreen("This is profile", appState, innerPadding)
    }
}

fun NavGraphBuilder.MyBookingsGraph(
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    composable(route = myBookingsNavigationRoute) {
        TestScreen("This is my bookings", appState, innerPadding)
    }
}

fun NavGraphBuilder.InvitationsGraph(
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    composable(route = invitationsNavigationRoute) {
        TestScreen("This is invitations", appState, innerPadding)
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
            color = Color.Black
        )
    }
}

