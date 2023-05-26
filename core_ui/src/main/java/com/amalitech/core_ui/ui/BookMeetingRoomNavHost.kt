package com.amalitech.core_ui.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.amalitech.core_ui.state.BookMeetingRoomAppState

const val mainGraphNavigationDestination = "main_destination_route"

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
        MainGraph(
            startDestination = mainGraphNavigationDestination,
            appState = appState,
            innerPadding = innerPadding
        )
    }
}

const val mainNavigationRoute = "main_route"
const val homeNavigationRoute = "home_route"
const val profileNavigationRoute = "profile_route"
const val myBookingsNavigationRoute = "my_bookings_route"
const val invitationsNavigationRoute = "invitations_route"

fun NavGraphBuilder.MainGraph(
    startDestination: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    navigation(
        route = mainNavigationRoute,
        startDestination = startDestination
    ) {
        HomeGraph(
            startDestination = homeNavigationRoute,
            appState,
            innerPadding
        )

        ProfileGraph(
            startDestination = profileNavigationRoute,
            appState,
            innerPadding
        )

        MyBookingsGraph(
            startDestination = myBookingsNavigationRoute,
            appState,
            innerPadding
        )

        InvitationsGraph(
            startDestination = invitationsNavigationRoute,
            appState,
            innerPadding
        )
    }
}

fun NavGraphBuilder.HomeGraph(
    startDestination: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    navigation(
        route = mainGraphNavigationDestination,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            Text(text = "This is home")
        }
    }
}

fun NavGraphBuilder.ProfileGraph(
    startDestination: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    navigation(
        route = mainGraphNavigationDestination,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            Text(text = "This is profile")
        }
    }
}

fun NavGraphBuilder.MyBookingsGraph(
    startDestination: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    navigation(
        route = mainGraphNavigationDestination,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            Text(text = "This is my bookings")
        }
    }
}

fun NavGraphBuilder.InvitationsGraph(
    startDestination: String,
    appState: BookMeetingRoomAppState,
    innerPadding: PaddingValues
) {
    navigation(
        route = mainGraphNavigationDestination,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            Text(text = "This is invitations")
        }
    }
}

