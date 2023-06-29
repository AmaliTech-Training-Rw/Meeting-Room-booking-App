package com.amalitech.home

import com.amalitech.home.components.HomeTab
import com.kizitonwose.calendar.core.CalendarDay
import java.time.YearMonth

data class HomeUiState(
    val tabs: List<HomeTab> = HomeTab.createHomeTabsList(),
    val selectedTab: HomeTab = tabs.first(),
    val currentMonth: YearMonth = YearMonth.now(),
    val currentSelectedDate: CalendarDay? = null,
)
