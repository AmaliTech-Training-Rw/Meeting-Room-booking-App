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
import com.amalitech.core_ui.ui.BookMeetingRoomDrawer
import com.amalitech.onboarding.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
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

                    // uncomment for drawer
                    val appState = rememberBookMeetingRoomAppState()
                    BookMeetingRoomDrawer(appState)
                }
            }
        }
    }
}
