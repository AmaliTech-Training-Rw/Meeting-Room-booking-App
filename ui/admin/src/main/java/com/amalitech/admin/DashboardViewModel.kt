package com.amalitech.admin

import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.dashboard.use_case.FetchDashboardDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val useCase: FetchDashboardDataUseCase
) : BaseViewModel<Nothing>() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }
            val result = useCase()
            result.data?.let { dashboardData ->
                _uiState.update { dashboardUiState ->
                    dashboardUiState.copy(
                        rooms = dashboardData.rooms.map {
                            RoomsBookedTime(
                                roomName = it.name,
                                bookedTime = it.bookings.toFloat()
                            )
                        },
                        bookingsCount = dashboardData.bookings,
                        roomsCount = dashboardData.rooms.size,
                        usersCount = dashboardData.users,
                        loading = false
                    )
                }
            }
            result.error?.let { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        loading = false
                    )
                }
            }
        }
    }

    fun errorShown() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
