package com.amalitech.booking.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import kotlin.random.Random

@Composable
fun BookingHistoryScreen(
    appState: BookMeetingRoomAppState? = null,
    navigateUp: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val images = listOf(
        "https://picsum.photos/id/29/4000/2670",
        "https://picsum.photos/id/0/5000/3333",
        "https://picsum.photos/id/15/2500/1667",
        "https://picsum.photos/id/26/4209/2769",
        "https://picsum.photos/id/3/5000/3333",
        "https://picsum.photos/id/4/5000/3333",
        "https://picsum.photos/id/5/5000/3334",
        "https://picsum.photos/id/6/5000/3333",
        "https://picsum.photos/id/7/4728/3168",
        "https://picsum.photos/id/8/5000/3333",
        "https://picsum.photos/id/9/5000/3269",
        "https://picsum.photos/id/10/2500/1667",
        "https://picsum.photos/id/11/2500/1667",
        "https://picsum.photos/id/12/2500/1667",
        "https://picsum.photos/id/13/2500/1667",
        "https://picsum.photos/id/1/5000/3333",
        "https://picsum.photos/id/14/2500/1667",
        "https://picsum.photos/id/16/2500/1667",
        "https://picsum.photos/id/17/2500/1667",
        "https://picsum.photos/id/10/2500/1667",
        "https://picsum.photos/id/18/2500/1667",
        "https://picsum.photos/id/19/2500/1667",
        "https://picsum.photos/id/20/3670/2462",
        "https://picsum.photos/id/22/4434/3729",
        "https://picsum.photos/id/9/5000/3269",
        "https://picsum.photos/id/23/3887/4899",
        "https://picsum.photos/id/24/4855/1803",
        "https://picsum.photos/id/25/5000/3333",
        "https://picsum.photos/id/27/3264/1836",
        "https://picsum.photos/id/28/4928/3264",
        "https://picsum.photos/id/33/5000/3333",
        "https://picsum.photos/id/30/1280/901",
        "https://picsum.photos/id/31/3264/4912",
        "https://picsum.photos/id/32/4032/3024"
    )
    val names = listOf(
        "Vessel Of Light",
        "Inspiration Lounge",
        "The Portable Space",
        "Think Out Loud",
        "IdeaWorks",
        "Thought Out",
        "Living The Story",
        "Wishpiration",
        "Nature Lovers",
        "Sharing Is Caring",
        "Vision 2020",
        "Eternal Hopes",
        "Vision Achievers",
        "One Goal",
        "One Vision",
        "Growing Horizon",
        "Success Majors",
        "Smart Choices",
        "Burning Desire",
        "Mind Conference",
        "Achievement Territory",
        "Fortune Seekers",
        "Idea Advancements",
        "Goal Oriented Minds",
        "Proficiency Group",
        "Group Effort",
        "Agents Of Change",
        "The Good Guys",
        "Focus Faction",
        "Success Cartel",
        "Winners Circle",
        "Inner Winners",
        "Stress Success",
        "Mind Binds"
    )
    val bookings = (0..33).map {
        val startTime = LocalTime.of(Random.nextInt(0, 23), 0)
        Booking(
            id = it.toString(),
            roomName = names[it],
            LocalDate.now(),
            startTime,
            startTime.plusHours(Random.nextLong(1, 4)),
            imgUrl = images[it],
            bookedBy = "John Doe"
        )
    }
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
        contentPadding = PaddingValues(vertical = spacing.spaceSmall),
        modifier = Modifier.padding(horizontal = spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
    ) {
        items(bookings) { booking ->
            BookingRequestItem(booking = booking, onClick = {})
        }
    }
}
