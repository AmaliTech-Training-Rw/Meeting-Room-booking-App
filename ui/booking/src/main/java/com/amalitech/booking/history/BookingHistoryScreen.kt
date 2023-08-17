package com.amalitech.booking.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.components.BookingRequestItem
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.ui.booking.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun BookingHistoryScreen(
    appState: BookMeetingRoomAppState? = null,
    navigateUp: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val bookings = listOf(
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeho.com/200.png",
            "John Doe"
        ),
    )
    val spacing = LocalSpacing.current
    val title = stringResource(R.string.booking_history)
    val scope = rememberCoroutineScope()

    CustomBackHandler(appState = appState, onComposing = onComposing) {
        navigateUp()
    }
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = title,
                navigationIcon = {
                    NavigationButton {
                        scope.launch {
                            appState?.drawerState?.open()
                        }
                    }
                },
                actions = {
                    PainterActionButton {
                        navigateToProfileScreen()
                    }
                },
            )
        )
    }

    LazyColumn(
        modifier = Modifier.padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {
        items(bookings) { booking ->
            BookingRequestItem(booking = booking, onClick = {})
        }
    }
}
