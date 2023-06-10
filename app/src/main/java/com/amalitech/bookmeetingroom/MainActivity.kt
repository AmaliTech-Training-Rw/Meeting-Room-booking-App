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
import com.amalitech.admin.components.DashboardBarGraph
import com.amalitech.admin.components.RoomsBookedTime
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import kotlin.random.Random

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

                    val items = (1..26).map {
                        RoomsBookedTime(
                            it,
                            Random.nextInt(80, 150).toFloat(),
                            "room$it"
                        )
                    }
                    DashboardBarGraph(items)
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
