package com.amalitech.core_ui.bottom_navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMeetingTopAppBar(
    appState: BookMeetingRoomAppState
) {
    TopAppBar(
        title = {
            Text(
                "MyFitness App",
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
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookMeetingTopAppBarPreview() {
    BookMeetingRoomTheme {
        val appState = rememberBookMeetingRoomAppState()
        BookMeetingTopAppBar(appState)
    }
}
