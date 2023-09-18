package com.amalitech.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.data.model.Room
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.calendar.BookingUiState
import com.amalitech.home.calendar.CalendarUiState
import com.amalitech.home.use_case.HomeUseCaseWrapper
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val homeUseCase: HomeUseCaseWrapper,
    sharedPref: OnboardingSharedPreferences
) : BaseViewModel<CalendarUiState>() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> get() = _uiState
    private val _isUsingAdminDashboard =
        mutableStateOf(if (sharedPref.isUserAdmin()) sharedPref.loadAdminUserScreen() else false)
    val isUsingAdminDashboard: State<Boolean> get() = _isUsingAdminDashboard
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    private var roomsCopy: List<Room> = emptyList()

    init {
        refreshBookings()
        fetchRooms()
        onCurrentDayChange(CalendarDay(LocalDate.now(), position = DayPosition.MonthDate))
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

    fun fetchRooms() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val result = homeUseCase.fetchRoomsUseCase()
            result.data?.let {
                roomsCopy = it
                _uiState.value = _uiState.value.copy(rooms = it, loading = false)
            }
            result.error?.let {
                _uiState.value = _uiState.value.copy(error = it, loading = false)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    internal fun toBookingsUiStateMap(data: List<Booking>) =
        data
            .map { booking: Booking ->
                BookingUiState(
                    booking.startTime,
                    booking.endTime,
                    booking.roomName,
                    booking.date
                )
            }
            .groupBy {
                it.date
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

    fun onSelectedTabChange(tab: Tab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun onNewSearchQuery(query: String) {
        _searchQuery.value = query
        onSearch()
    }

    fun onSearch() {
        _uiState.value = _uiState.value.copy(
            rooms = roomsCopy.filter {  room ->
                room.roomName.contains(_searchQuery.value, true) ||
                        room.roomFeatures.any { it.contains(_searchQuery.value, true) }
                        || room.numberOfPeople.toString().contains(_searchQuery.value, true)
            }
        )
    }

    fun resetList() {
        _uiState.value = _uiState.value.copy(rooms = roomsCopy)
    }
}