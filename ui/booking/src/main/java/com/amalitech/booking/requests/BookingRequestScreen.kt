package com.amalitech.booking.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.components.BookingRequestCard
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingRequestScreen(
    viewModel: BookingRequestViewModel = koinViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val isLoading = uiState is UiState.Loading
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Error -> {
                (uiState as UiState.Error).error?.let {
                    snackbarHostState.showSnackbar(
                        it.asString(context)
                    )
                    viewModel.onSnackBarShown()
                }
            }

            else -> {}
        }
    }

    Box(Modifier.fillMaxSize()) {
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
                ) {
                    (uiState as UiState.Success<List<Booking>>).data?.let { bookings ->
                        items(bookings) { booking ->
                            BookingRequestCard(booking = booking, modifier = Modifier.height(120.dp), onRightContentClick = {  }) {

                            }
//                            BookingRequestItem(booking = booking)
                        }
                    }
                }
            }

            else -> {}
        }
    }
}
