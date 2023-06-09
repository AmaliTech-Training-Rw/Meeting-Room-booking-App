package com.amalitech.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.R
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.calendar.BookingUiState
import com.amalitech.home.calendar.CalendarUiState
import com.amalitech.home.components.HomeTab
import com.amalitech.home.model.Booking
import com.amalitech.home.use_case.HomeUseCase
import com.kizitonwose.calendar.core.CalendarDay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: HomeUseCase
) : BaseViewModel<CalendarUiState>() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> get () = _uiState

    init {
        refreshBookings()
    }

    /**
     * refreshBookings - Refreshes list of bookings for the current year
     * it checks if value of _uiStateFlow is UiState.Success, then it takes the year
     * associated with the current month. Otherwise, the current year is used.
     *
     * Values of bookings gotten from the api will be arranged by date
     * to make it easier for the calendar to access them. The _uiStateFlow variable is
     * updated with these values.
     */
    fun refreshBookings() {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            val year = _uiState.value.currentMonth.year
            _uiStateFlow.update {
                UiState.Loading()
            }

            val response = homeUseCase.fetchBookings(year = year)
            if (response.data != null) {
                val bookings = toBookingsUiStateMap(response.data!!)
                _uiStateFlow.update {
                    UiState.Success(
                        CalendarUiState(
                            bookings = bookings
                        )
                    )
                }
                updateBookingsForDay()
            } else if (response.error != null) {
                _uiStateFlow.update {
                    UiState.Error(response.error)
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(
                        UiText.StringResource(R.string.generic_error)
                    )
                }
            }
        }
    }

    internal fun toBookingsUiStateMap(data: List<Booking>) =
        data
            .map { booking: Booking ->
                BookingUiState(
                    booking.startTime,
                    booking.endTime,
                    booking.roomName
                )
            }
            .groupBy {
                it.startTime.toLocalDate()
            }

    fun onCurrentDayChange(day: CalendarDay?) {
        _uiState.value = _uiState.value.copy(
            currentSelectedDate = day
        )
        updateBookingsForDay()
    }

    private fun updateBookingsForDay() {
        when (_uiStateFlow.value) {
            is UiState.Success -> {
                _uiStateFlow.update { state ->
                    val successState = (state as UiState.Success).data
                    val bookingsForDay =
                        successState?.bookings?.get(_uiState.value.currentSelectedDate?.date)
                            ?: emptyList()
                    UiState.Success(
                        data = successState?.copy(
                            bookingsForDay = bookingsForDay
                        )
                    )
                }
            }

            else -> {}
        }
    }

    fun onSelectedTabChange(tab: HomeTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }
}