package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.components.BookMeetingRoomDrawer
import com.amalitech.onboarding.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                // TODO: uncomment this while testing, and move to appropriate position ...
                // val appState = rememberBookMeetingRoomAppState()
                // BookMeetingRoomDrawer(appState)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onNavigateToHome = { /*TODO*/ },
                        onNavigateToForgotPassword = { /*TODO*/ },
                        onNavigateToSignUp = { /*TODO*/ },
                        onNavigateUp = { /*TODO*/ })
                }
            }
        }
    }
}
