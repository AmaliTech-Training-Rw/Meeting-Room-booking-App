package com.amalitech.booking.history

import androidx.lifecycle.viewModelScope
import com.amalitech.booking.model.Booking
import com.amalitech.booking.requests.BookingRequestsUiState
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingHistoryViewModel(
    private val useCase: FetchBookingHistoryUseCase
): BaseViewModel<BookingRequestsUiState>() {
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
                it.copy(
                    isLoading = true
                )
            }

            val result = useCase()

            if (result.data != null) {
                bookingsCopy = result.data ?: emptyList()
                _uiState.update {
                    it.copy(
                        bookings = bookingsCopy,
                        isLoading = false
                    )
                }
            } else if (result.error != null) {
                _uiState.update {
                    it.copy(
                        error = result.error,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(com.amalitech.core.R.string.error_default_message),
                        isLoading = false
                    )
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
