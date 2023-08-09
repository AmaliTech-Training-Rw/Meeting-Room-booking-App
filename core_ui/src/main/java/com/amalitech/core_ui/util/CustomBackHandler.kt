package com.amalitech.core_ui.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomBackHandler(
    appState: BookMeetingRoomAppState?,
    scope: CoroutineScope,
    onComposing: (AppBarState) -> Unit,
    navigateUp: () -> Unit
) {
    BackHandler {
        if (appState?.drawerState?.isClosed == true) {
            navigateUp()
            onComposing(AppBarState())
        } else {
            scope.launch {
                appState?.drawerState?.close()
            }
        }
    }
}

fun handleDrawer(
    appState: BookMeetingRoomAppState?,
    scope: CoroutineScope
) {
    if (appState?.drawerState?.isOpen == true) {
        scope.launch { appState.drawerState.close() }
    } else {
        scope.launch { appState?.drawerState?.open() }
    }
}

