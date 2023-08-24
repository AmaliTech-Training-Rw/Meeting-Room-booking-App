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
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.BookMeetingTopAppBar
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

@Composable
fun BookMeetingRoomApp(
    appState: BookMeetingRoomAppState,
    mainNavController: NavHostController,
    onFinishActivity: () -> Unit
) {
//    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }
    var appBarState by remember {
        mutableStateOf(AppBarState())
    }

    Scaffold(
        topBar = {
            BookMeetingTopAppBar(
                appBarState = appBarState
            )
        },
        content = { innerPadding ->
            BookMeetingRoomNavHost(
                innerPadding = innerPadding,
                startDestination = NavigationItem.Dashboard.route,
                appState = appState,
                onComposing = {
                    appBarState = it
                },
                onFinishActivity = onFinishActivity,
                mainNavController
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
//            // Show the Floating Action Button on top level destinations (you can also go ahead and exclude some).
//            val destination = appState.currentTopLevelDestination
//            if (destination != null) {
//                FloatingActionButton(
//                    onClick = {
//                        when(destination.title) {
//                            "Users" -> {
//                                fabOnClick?.invoke()
//                            }
//                        }
//                    },
//                    shape = CircleShape,
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                ) {
//                    Icon(
//                        imageVector = Icons.Rounded.Add,
//                        contentDescription = "Add FAB",
//                        tint = Color.White,
//                    )
//                }
//            }
            appBarState.floatingActionButton?.invoke()
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
            mainNavController = rememberNavController()
        ) { }
    }
}

