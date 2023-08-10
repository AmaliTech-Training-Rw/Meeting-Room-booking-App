package com.amalitech.core_ui.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import kotlinx.coroutines.launch

@Composable
fun CustomBackHandler(
    appState: BookMeetingRoomAppState?,
    onComposing: (AppBarState) -> Unit,
    navigateUp: () -> Unit
) {
    val scope = rememberCoroutineScope()
    BackHandler {
        if (appState?.drawerState?.isClosed == true || appState == null) {
            navigateUp()
            onComposing(AppBarState())
        } else {
            scope.launch {
                appState.drawerState.close()
            }
        }
    }
}
