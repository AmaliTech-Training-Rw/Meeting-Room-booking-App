package com.amalitech.core_ui.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.example.core_ui.ui.theme.BookMeetingRoomTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMeetingRoomTopAppBar(appState: BookMeetingRoomAppState) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    TopAppBar(
        title = {
            Text(
                "Dashboard",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (appState.drawerState.isClosed) {
                    appState.coroutineScope.launch {
                        appState.drawerState.open()
                    }
                } else {
                    appState.coroutineScope.launch {
                        appState.drawerState.close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview(showBackground = true)
@Composable
fun BookMeetingRoomTopAppBarPreview() {
    val appState = rememberBookMeetingRoomAppState()
    BookMeetingRoomTheme {
        BookMeetingRoomTopAppBar(appState)
    }
}