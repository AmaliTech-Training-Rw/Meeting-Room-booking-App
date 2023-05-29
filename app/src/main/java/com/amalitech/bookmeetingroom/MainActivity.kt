package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.amalitech.core_ui.components.drawer.BookMeetingRoomDrawer
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberBookMeetingRoomAppState()
            BookMeetingRoomTheme {
                BookMeetingRoomDrawer(appState)
            }
        }
    }
}
