package com.amalitech.core_ui.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.amalitech.core_ui.navigation.BookMeetingRoomNavHost
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem

@Composable
fun BookMeetingRoomApp(
    appState: BookMeetingRoomAppState,
    title: String
) {
    Scaffold(
        topBar = {
            // Show the top app bar on top level destinations.
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                // TODO: replace with topbar
            }
        },
        content = { innerPadding ->
            BookMeetingRoomNavHost(
                        innerPadding,
                NavigationItem.Home.route,
                        appState
                    )
                }
            )

}
