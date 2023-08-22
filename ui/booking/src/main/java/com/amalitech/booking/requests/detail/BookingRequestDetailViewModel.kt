package com.amalitech.booking.requests.detail

import androidx.lifecycle.viewModelScope
import com.amalitech.booking.request.detail.GetBookingRequestDetailUseCase
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingRequestDetailViewModel(
    private val useCase: GetBookingRequestDetailUseCase
) : BaseViewModel<BookingRequestDetailUiState>() {
    private val _uiState: MutableStateFlow<BookingRequestDetailUiState> = MutableStateFlow(
        BookingRequestDetailUiState()
    )
    val uiState: StateFlow<BookingRequestDetailUiState> get() = _uiState.asStateFlow()

    fun getBookingDetail(bookingId: String) {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val result = useCase(bookingId)
            val data = result.data

            if (data != null) {
                _uiState.update {
                    it.copy(
                        bookingRequestDetail = data,
                        isLoading = false
                    )
                }
            } else if (result.error != null) {
                _uiState.update {
                    it.copy(error = result.error)
                }
            } else {
                _uiState.update {
                    it.copy(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
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
