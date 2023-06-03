package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.core_ui.components.DashboardGraph
import com.amalitech.core_ui.components.RoomsBookedTime
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .scrollable(
                            orientation = Orientation.Vertical,
                            state = rememberScrollState()
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val itemsBookedTime = listOf(
                        30f,
                        9f,
                        17f,
                        20f,
                        26f,
                        29f,
                        5f,
                        4f
                    )
                    val items = (1..8).map {
                        RoomsBookedTime(
                            it,
                            itemsBookedTime[it - 1],
                            "room$it"
                        )
                    }
                   DashboardGraph(items)
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
