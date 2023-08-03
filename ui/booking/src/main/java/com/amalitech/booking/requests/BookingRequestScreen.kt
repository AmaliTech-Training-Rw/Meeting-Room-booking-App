package com.amalitech.booking.requests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.components.BookingRequestCard
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingRequestScreen(
    modifier: Modifier = Modifier,
    viewModel: BookingRequestViewModel = koinViewModel(),
    navigateToDetail: (booking: Booking) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            SnackbarManager.showMessage(
                SnackbarMessage.StringSnackbar(it.asString(context))
            )
            viewModel.clearError()
        }
    }

    Box(modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {
            items(uiState.bookings) { booking ->
                BookingRequestCard(
                    booking = booking,
                    modifier = Modifier.height(120.dp),
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
    }
}
