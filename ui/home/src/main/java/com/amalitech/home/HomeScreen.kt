package com.amalitech.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.home.calendar.CalendarScreen
import com.amalitech.home.components.HomeTab
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
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
        HomeTab(
            onTabSelected = { tab ->
                viewModel.onSelectedTabChange(tab)
            },
            tabs = tabs,
            selectedTab = selectedTab,
            modifier = Modifier.height(40.dp)
        )
        when (selectedTab) {
            HomeTab.Calendar -> {
                CalendarScreen()
            }
            HomeTab.Rooms -> {}
        }
    }
}
