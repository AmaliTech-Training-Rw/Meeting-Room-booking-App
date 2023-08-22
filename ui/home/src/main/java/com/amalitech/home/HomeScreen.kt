package com.amalitech.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.BookingAppTab
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.home.calendar.CalendarScreen
import com.amalitech.home.room.components.RoomItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    appState: BookMeetingRoomAppState? = null,
    onComposing: (AppBarState) -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateUp: () -> Unit,
    navigateToBookRoomScreen: (roomId: String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val uiState = viewModel.uiState.value
    val selectedTab = uiState.selectedTab
    val tabs = uiState.tabs
    val homeTitle = stringResource(id = com.amalitech.core_ui.R.string.home)
    val isUsingAdminDashboard by viewModel.isUsingAdminDashboard
    val scope = rememberCoroutineScope()

    CustomBackHandler(appState = appState, onComposing = onComposing) {
        navigateUp()
    }
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = homeTitle,
                navigationIcon = {
                    if (isUsingAdminDashboard) {
                        NavigationButton {
                            scope.launch {
                                appState?.drawerState?.open()
                            }
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.spaceMedium),
    ) {
        BookingAppTab(
            onTabSelected = { tab ->
                viewModel.onSelectedTabChange(tab)
            },
            tabs = tabs,
            selectedTab = selectedTab,
            modifier = Modifier.height(48.dp)
        )
        when (selectedTab) {
            Tab.Calendar -> {
                CalendarScreen()
            }

            Tab.Rooms -> {
                // TODO("ADD ROOM SCREEN HERE")
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
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
                        "https://picsum.photos/id/26/4209/2769",
                        "https://picsum.photos/id/17/2500/1667",
                        "https://picsum.photos/id/18/2500/1667",
                        "https://picsum.photos/id/19/2500/1667",
                        "https://picsum.photos/id/20/3670/2462",
                        "https://picsum.photos/id/22/4434/3729",
                        "https://picsum.photos/id/23/3887/4899",
                        "https://picsum.photos/id/24/4855/1803",
                        "https://picsum.photos/id/25/5000/3333",
                        "https://picsum.photos/id/27/3264/1836",
                        "https://picsum.photos/id/28/4928/3264",
                        "https://picsum.photos/id/23/3887/4899",
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
                    val items = (0..33).map {
                        Room(
                            id = "id$it",
                            roomName = names[it],
                            numberOfPeople = Random.nextInt(2, 20),
                            roomFeatures = listOf(
                                "AC",
                                "Wi-Fi",
                                "Whiteboard",
                                "Lighting",
                                "Speakers",
                                "Drinks"
                            ),
                            imageUrl = images[it]
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    }
                    items(items.size) { index ->
                        RoomItem(
                            room = items[index],
                            modifier = Modifier
                                .height(150.dp)
                                .padding(
                                    start = spacing.spaceExtraSmall,
                                    end = spacing.spaceExtraSmall,
                                    bottom = spacing.spaceExtraSmall,
                                )
                                .shadow(2.dp, RoundedCornerShape(spacing.spaceMedium))
                                .background(MaterialTheme.colorScheme.background),
                            onBookRoom = { navigateToBookRoomScreen(items[index].id) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    }
                }
            }

            else -> {}
        }
    }
}
