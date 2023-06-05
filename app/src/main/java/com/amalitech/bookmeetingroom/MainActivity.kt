package com.amalitech.bookmeetingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                        .size(400.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val itemsBookedTime = listOf(
                        9f,
                        17f,
                        30f,
                        20f,
                        26f,
                        29f,
                        50f,
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
