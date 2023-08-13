package com.amalitech.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                    if(isUsingAdminDashboard) {
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
            .padding(spacing.spaceMedium)
    ) {
        BookingAppTab(
            onTabSelected = { tab ->
                viewModel.onSelectedTabChange(tab)
            },
            tabs = tabs,
            selectedTab = selectedTab,
            modifier = Modifier.height(40.dp)
        )
        when (selectedTab) {
            Tab.Calendar -> {
                CalendarScreen()
            }

            Tab.Rooms -> {
                // TODO("ADD ROOM SCREEN HERE")
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                    modifier = Modifier.padding(top = spacing.spaceSmall)
                ) {
                    val items = (1..20).map {
                        Room(
                            id = "id$it",
                            roomName = "Room $it",
                            numberOfPeople = Random.nextInt(2, 20),
                            roomFeatures = listOf(
                                "Air conditioning",
                                "Internet",
                                "Whiteboard",
                                "Natural light",
                                "Drinks"
                            ),
                            imageUrl = "https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png"
                        )
                    }

                    items(items) { room ->
                        RoomItem(
                            room = room,
                            modifier = Modifier
                                .height(150.dp)
                                .padding(
                                    start = spacing.spaceExtraSmall,
                                    end = spacing.spaceExtraSmall,
                                    bottom = spacing.spaceExtraSmall,
                                )
                                .shadow(spacing.spaceExtraSmall, RoundedCornerShape(spacing.spaceMedium))
                                .background(MaterialTheme.colorScheme.background),
                            onBookRoom = { navigateToBookRoomScreen(room.id) }
                        )
                    }
                }
            }

            else -> {}
        }
    }
}
