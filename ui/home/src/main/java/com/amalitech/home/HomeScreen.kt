package com.amalitech.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.BookingAppTab
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.components.SearchIcon
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.home.calendar.CalendarScreen
import com.amalitech.home.room.components.RoomItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    appState: BookMeetingRoomAppState? = null,
    showSnackBar: (message: String) -> Unit,
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
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val items = uiState.rooms
    val context = LocalContext.current

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
                    SearchIcon(
                        searchQuery = viewModel.searchQuery.value,
                        onSearch = {
                            viewModel.onSearch()
                        },
                        onSearchQueryChange = { query ->
                            viewModel.onNewSearchQuery(query)
                        },
                        isSearchTextFieldVisible = isSearchVisible,
                        onSearchTextFieldVisibilityChanged = {
                            isSearchVisible = it
                            if (!it) {
                                viewModel.resetList()
                            }
                        }
                    )
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
                LaunchedEffect(key1 = uiState) {
                    uiState.error?.let {
                        showSnackBar(it.asString(context))
                        viewModel.clearError()
                    }
                }
                Box(Modifier.fillMaxSize()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                    ) {
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
                    if (uiState.loading)
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            else -> {}
        }
    }
}
