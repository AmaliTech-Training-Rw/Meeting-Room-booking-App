package com.amalitech.booking.requests

import androidx.lifecycle.viewModelScope
import com.amalitech.booking.model.Booking
import com.amalitech.booking.request.use_case.BookingRequestsUseCaseWrapper
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingRequestViewModel(
    private val useCaseWrapper: BookingRequestsUseCaseWrapper
) : BaseViewModel<BookingRequestsUiState>() {

    private val _uiState = MutableStateFlow(BookingRequestsUiState())
    val uiState: StateFlow<BookingRequestsUiState> get() = _uiState.asStateFlow()

    init {
        fetchBookings()
    }

    internal fun fetchBookings() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiState.update {
                BookingRequestsUiState(
                    isLoading = true
                )
            }

            val result = useCaseWrapper.fetchBookingsUseCase()

            if (result.data != null) {
                _uiState.update {
                    BookingRequestsUiState(
                        bookings = result.data ?: emptyList()
                    )
                }
            } else if (result.error != null) {
                BookingRequestsUiState(
                    error = result.error
                )
            } else {
                BookingRequestsUiState(
                    error = UiText.StringResource(com.amalitech.core.R.string.error_default_message)
                )
            }
        }
    }

    fun onApproved(booking: Booking) {
        updateStatus(booking, true)
    }

    fun onDecline(booking: Booking) {
        updateStatus(booking, false)
    }

    internal fun updateStatus(booking: Booking, isApproved: Boolean) {
        viewModelScope.launch {
            val result = useCaseWrapper.updateBookingStatusUseCase(
                isApproved = isApproved,
                booking = booking
            )
            if (result == null) {
                fetchBookings()
            } else {
                _uiState.update {
                    it.copy(error = result)
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
