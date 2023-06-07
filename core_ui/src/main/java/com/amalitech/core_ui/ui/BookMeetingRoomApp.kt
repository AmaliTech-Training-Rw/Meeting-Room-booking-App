package com.amalitech.core_ui.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.core_ui.components.BookMeetingTopAppBar
import com.amalitech.core_ui.navigation.BookMeetingRoomNavHost
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

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
                BookMeetingTopAppBar(appState, title)
            }
        },
        content = { innerPadding ->
            BookMeetingRoomNavHost(
                innerPadding,
                NavigationItem.Home.route,
                appState)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    )

}

@Preview(showBackground = true)
@Composable
fun BookMeetingTopAppBarPreview() {
    BookMeetingRoomTheme {
        val appState = rememberBookMeetingRoomAppState()
        BookMeetingRoomApp(appState, "Home")
    }
}

