package com.amalitech.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.components.BookingAppTab
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.home.calendar.CalendarScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onBookRoom: (roomId: String) -> Unit
) {
    val spacing = LocalSpacing.current
    val uiState = viewModel.uiState.value
    val selectedTab = uiState.selectedTab
    val tabs = uiState.tabs

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
                LazyColumn {
                    val items = (1..20).toList()
                    items(items) {roomId ->
                        Button(onClick = { onBookRoom(roomId.toString()) }) {
                            Text("Book a room $roomId")
                        }
                    }
                }
            }
            else -> {}
        }
    }
}
