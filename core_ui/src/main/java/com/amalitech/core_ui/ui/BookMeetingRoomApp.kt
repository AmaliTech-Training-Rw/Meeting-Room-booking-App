package com.amalitech.core_ui.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.example.core_ui.ui.theme.BookMeetingRoomTheme

@Composable
fun BookMeetingRoomApp(appState: BookMeetingRoomAppState) {
    BookMeetingRoomTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    // Show the top app bar on top level destinations.
                    val destination = appState.currentTopLevelDestination
                    if (destination != null) {
                        BookMeetingRoomTopAppBar(appState)
                    }
                },
                content = { innerPadding ->
                    BookMeetingRoomNavHost(
                        innerPadding,
                        mainNavigationRoute,
                        appState
                    )
                }
            )
        }
    }
}
