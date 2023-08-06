package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.amalitech.core_ui.components.BookMeetingTopAppBar
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.rooms.FloatingActionButton

@Composable
fun BookMeetingRoomApp(
    appState: BookMeetingRoomAppState,
    title: String,
    searchQuery: String? = null,
    onSearchQueryChange: ((String) -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    isSearchTextFieldVisible: Boolean = false,
    onSearchTextFieldVisibilityChange: ((Boolean) -> Unit)? = null,
    mainNavController: NavHostController
) {
    var floatingActionButton by remember {
        mutableStateOf(FloatingActionButton())
    }

    Scaffold(
        topBar = {
            // Show the top app bar on top level destinations.
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                BookMeetingTopAppBar(
                    appState = appState,
                    title = title,
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    onSearchClick = onSearchClick,
                    isSearchTextFieldVisible = isSearchTextFieldVisible,
                    onSearchTextFieldVisibilityChange = onSearchTextFieldVisibilityChange
                )
            }
        },
        content = { innerPadding ->
            BookMeetingRoomNavHost(
                innerPadding,
                NavigationItem.Home.route,
                appState,
                mainNavController,
                onComposing = { floatingActionButton = it }
            )
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
        },
        floatingActionButton = {
            floatingActionButton.action?.invoke()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookMeetingTopAppBarPreview() {
    BookMeetingRoomTheme {
        val appState = rememberBookMeetingRoomAppState()
        BookMeetingRoomApp(
            appState = appState,
            title = "Home",
            mainNavController = rememberNavController()
        )
    }
}

