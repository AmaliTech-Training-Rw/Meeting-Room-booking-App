package com.amalitech.booking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.booking.use_case.BookingUseCase
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingViewModel(
    private val useCase: BookingUseCase
) : BaseViewModel<BookingUiState>() {
    val tabs = Tab.createBookingTabsList()
    private val _selectedTab = mutableStateOf(tabs.first())
    val selectedTab: State<Tab> get() = _selectedTab

    init {
        fetchBookings()
    }

    fun fetchBookings(ended: Boolean = false) {
        if (job?.isActive == true)
            job?.cancel()
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val response = useCase.getBookingsUseCase(ended)
            val data = response.data

            if (data != null) {
                _uiStateFlow.update {
                    UiState.Success(data = BookingUiState(data))
                }
            } else if (response.error != null) {
                _uiStateFlow.update {
                    UiState.Error(error = response.error)
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(error = UiText.StringResource(com.amalitech.core.R.string.error_default_message))
                }
            }
        }
    }

    fun onTabSelected(tab: Tab) {
        _selectedTab.value = tab
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
