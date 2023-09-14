package com.amalitech.booking.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.components.BookingRequestCard
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.EmptyListScreen
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.components.SearchIcon
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.ui.booking.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingRequestScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingRequestViewModel = koinViewModel(),
    navigateUp: () -> Unit,
    appState: BookMeetingRoomAppState? = null,
    onComposing: (AppBarState) -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateToDetail: (booking: Booking) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val scope = rememberCoroutineScope()
    val title = stringResource(R.string.booking_request)
    var isSearchQueryVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            appState?.snackbarHostState?.showSnackbar(it.asString(context))
            viewModel.clearError()
        }
    }

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
                    SearchIcon(
                        searchQuery = uiState.searchQuery,
                        onSearch = viewModel::onSearch,
                        onSearchQueryChange = {
                            viewModel.onNewSearchQuery(it)
                        },
                        isSearchTextFieldVisible = isSearchQueryVisible,
                        onSearchTextFieldVisibilityChanged = {
                            isSearchQueryVisible = it
                            if (!isSearchQueryVisible) {
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

    Box(
        modifier
            .fillMaxSize()
            .padding(horizontal = spacing.spaceMedium)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium),
            contentPadding = PaddingValues(spacing.spaceSmall)
        ) {
            items(uiState.bookings) { booking ->
                BookingRequestCard(
                    booking = booking,
                    modifier = Modifier.height(80.dp),
                    onRightContentClick = {
                        viewModel.onDecline(booking)
                    },
                    onLeftContentClick = {
                        viewModel.onApproved(booking)
                    }
                ) { clickedBooking ->
                    navigateToDetail(clickedBooking)
                }
            }
        }
        if (uiState.bookings.isEmpty() && !uiState.isLoading)
            EmptyListScreen(item = stringResource(R.string.booking_requests))
    }
}
