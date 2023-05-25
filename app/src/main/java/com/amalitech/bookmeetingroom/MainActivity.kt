package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.authentication.login.LoginScreen
import com.example.core_ui.ui.theme.BookMeetingRoomTheme

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
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookMeetingRoomTheme {
        Greeting("Android")
    }
}
