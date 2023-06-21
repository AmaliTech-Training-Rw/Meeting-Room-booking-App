package com.amalitech.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.amalitech.core_ui.components.BookMeetingTopAppBar
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.rooms.components.RoomCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoomListScreen(
    roomViewModel: RoomViewModel =  koinViewModel()
) {
    val spacing = LocalSpacing.current

    Scaffold(
        topBar = {
            val appState = rememberBookMeetingRoomAppState()
            BookMeetingTopAppBar(appState, "Rooms")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Handle floating action button click */ },
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(spacing.spaceMedium),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = spacing.spaceMedium)
                ){
                    items(roomViewModel.rooms.value) { room ->
                        RoomCard(room = room, modifier = Modifier.padding(bottom = spacing.spaceMedium))
                    }
                }

            }
        }
    )
}
