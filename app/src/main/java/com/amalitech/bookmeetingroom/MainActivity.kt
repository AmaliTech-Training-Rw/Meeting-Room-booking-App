package com.amalitech.bookmeetingroom

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.components.BookingRequestItem
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookMeetingRoomTheme {
                Column(modifier = Modifier.padding(12.dp)) {
                    BookingRequestItem(
                        Booking(
                            "room 1",
                            LocalDate.now(),
                            LocalTime.now(),
                            LocalTime.now().plusHours(4),
                            "https://via.placeholder.com/200.png",
                            "Ngomde Cadet Kamdaou"
                        ),
                        modifier = Modifier.height(100.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    BookingRequestItem(
                        Booking(
                            "room 1",
                            LocalDate.now(),
                            LocalTime.now(),
                            LocalTime.now().plusHours(4),
                            "https://via.placeholder.com/500.png",
                            "Ngomde Cadet Kamdaou"
                        ),
                        modifier = Modifier.height(100.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    BookingRequestItem(
                        Booking(
                            "room 1",
                            LocalDate.now(),
                            LocalTime.now(),
                            LocalTime.now().plusHours(4),
                            "https://via.placeholdr.com/500.png",
                            "Ngomde Cadet Kamdaou"
                        ),
                        modifier = Modifier.height(100.dp)
                    )
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