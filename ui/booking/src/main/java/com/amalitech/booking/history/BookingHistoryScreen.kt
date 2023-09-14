package com.amalitech.booking.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.booking.requests.components.BookingRequestItem
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
fun BookingHistoryScreen(
    appState: BookMeetingRoomAppState? = null,
    viewModel: BookingHistoryViewModel = koinViewModel(),
    navigateUp: () -> Unit,
    onComposing: (AppBarState) -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val title = stringResource(R.string.booking_history)
    val scope = rememberCoroutineScope()
    val searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    var isSearchVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val bookings = uiState.bookings
    val context = LocalContext.current

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
                        searchQuery = searchQuery,
                        onSearch = viewModel::onSearch,
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

    LaunchedEffect(key1 = uiState) {
       uiState.error?.let { error ->
            appState?.snackbarHostState?.showSnackbar(
                error.asString(context = context)
            )
            viewModel.clearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = spacing.spaceSmall),
            modifier = Modifier.padding(horizontal = spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            items(bookings) { booking ->
                BookingRequestItem(booking = booking, onClick = {})
            }
        }
        if (uiState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        if (bookings.isEmpty() && !uiState.isLoading)
            EmptyListScreen(item = stringResource(R.string.booking_history_item))
    }
}
