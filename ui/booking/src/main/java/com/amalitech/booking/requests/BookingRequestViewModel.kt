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
    private var bookingsCopy: List<Booking> = emptyList()

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
                bookingsCopy = result.data ?: emptyList()
                _uiState.update {
                    BookingRequestsUiState(
                        bookings = bookingsCopy
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

    fun onNewSearchQuery(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
        onSearch()
    }

    fun onSearch() {
        _uiState.update { state ->
            state.copy(
                bookings = bookingsCopy.filter { booking ->
                    booking.roomName.contains(state.searchQuery, true) || booking.bookedBy.contains(
                        state.searchQuery,
                        true
                    ) || booking.attendees.any { it.contains(state.searchQuery, true) }
                }
            )
        }
    }

    fun resetList() {
        _uiState.update {
            it.copy(
                bookings = bookingsCopy,
                searchQuery = ""
            )
        }
    }
}
