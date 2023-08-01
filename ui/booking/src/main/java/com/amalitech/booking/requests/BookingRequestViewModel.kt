package com.amalitech.booking.requests

import androidx.lifecycle.viewModelScope
import com.amalitech.booking.model.Booking
import com.amalitech.booking.request.use_case.BookingRequestsUseCaseWrapper
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingRequestViewModel(
    private val useCaseWrapper: BookingRequestsUseCaseWrapper
) : BaseViewModel<List<Booking>>() {

    init {
        fetchBookings()
    }

    fun fetchBookings() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }

            val result = useCaseWrapper.fetchBookingsUseCase()

            if (result.data != null) {
                _uiStateFlow.update {
                    UiState.Success(result.data)
                }
            } else if (result.error != null) {
                _uiStateFlow.update {
                    UiState.Error(
                        result.error
                    )
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        UiText.StringResource(com.amalitech.core.R.string.error_default_message)
                    )
                }
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
                _uiStateFlow.update {
                    UiState.Error(result)
                }
            }
        }
    }

    fun lengthOfLongestSubstring(s: String): Int {
        var subStr = ""
        val currentLength: ArrayList<Int> = ArrayList()
        var i = 0
        while(i < s.length - 1) {
            for (j in i until s.length) {
                val c = s[j]
                if (!subStr.contains(c)) {
                    subStr += c
                } else {
                    currentLength.add(subStr.length)
                }
            }
            i++
        }
        val arr = currentLength.toTypedArray()
        print(arr)
        return arr.maxOrNull() ?: 0
    }
}
