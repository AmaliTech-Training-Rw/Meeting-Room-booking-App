package com.amalitech.home

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.components.Tab
import com.kizitonwose.calendar.core.CalendarDay
import java.time.YearMonth

data class HomeUiState(
    val tabs: List<Tab> = Tab.createHomeTabsList(),
    val selectedTab: Tab = tabs.first(),
    val currentMonth: YearMonth = YearMonth.now(),
    val currentSelectedDate: CalendarDay? = null,
    val rooms: List<Room> = emptyList(),
    val loading: Boolean = false,
    val error: UiText? = null
)
