package com.amalitech.booking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.booking.components.BookingItem
import com.amalitech.booking.model.Booking
import com.amalitech.core_ui.components.BookingAppTab
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.ui.booking.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingScreen(
    viewModel: BookingViewModel = koinViewModel()
) {
    val spacing = LocalSpacing.current
    val selectedTab by viewModel.selectedTab
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    var bookings: List<Booking>? by rememberSaveable {
        mutableStateOf(null)
    }
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Error -> {
                (uiState as UiState.Error<BookingUiState>).error?.let {
                    snackbarHostState.showSnackbar(
                        message = it.asString(context)
                    )
                    viewModel.onSnackBarShown()
                }
                isLoading = false
                bookings = null
            }

            is UiState.Success -> {
                bookings = (uiState as UiState.Success<BookingUiState>).data?.bookings
                isLoading = false
            }

            is UiState.Loading -> {
                isLoading = true
                bookings = null
            }

            else -> {}
        }
    }


    Box(
        Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Column {
            BookingAppTab(
                onTabSelected = { tab ->
                    viewModel.onTabSelected(tab)
                    val ended = tab is Tab.EndedBookings
                    viewModel.fetchBookings(ended = ended)
                },
                selectedTab = selectedTab,
                modifier = Modifier.height(40.dp),
                tabs = viewModel.tabs
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            if (bookings?.isEmpty() == true) {
                Text(
                    text = stringResource(R.string.no_item_found),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)) {
                    bookings?.let {
                        items(items = it) { item ->
                            BookingItem(
                                item = item,
                                modifier = Modifier.height(150.dp)
                            )
                        }
                    }
                }
            }
        }
        if (isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
