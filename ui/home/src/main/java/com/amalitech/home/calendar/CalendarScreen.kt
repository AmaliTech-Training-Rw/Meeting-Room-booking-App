package com.amalitech.home.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.HomeViewModel
import com.amalitech.home.calendar.components.BookingItem
import com.amalitech.home.calendar.components.Day
import com.amalitech.home.calendar.components.MonthHeader
import com.amalitech.home.calendar.components.SimpleCalendarTitle
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onToolBarColor: Color = MaterialTheme.colorScheme.onBackground,
    calendarBackgroundColor: Color = MaterialTheme.colorScheme.background,
    calendarContentColor: Color = MaterialTheme.colorScheme.onBackground,
    selectedDayContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    selectedDayBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    bookingsTitleBackgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    bookingsTitleContentColor: Color = MaterialTheme.colorScheme.onTertiary,
    bookingsContentColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    bookingsContentBackgroundColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val currentMonth by rememberSaveable {
        viewModel.currentMonth
    }
    val startMonth by rememberSaveable {
        mutableStateOf(currentMonth.minusMonths(500))
    }
    val endMonth by rememberSaveable {
        mutableStateOf(currentMonth.plusMonths(500))
    }
    val state by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    val selection by rememberSaveable {
        viewModel.currentSelectedDate
    }

    val daysOfWeek = rememberSaveable { daysOfWeek() }
    val uiState by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    var bookings = if (uiState is UiState.Success)
        (uiState as UiState.Success).data?.bookings ?: emptyMap()
    else emptyMap()
    val spacing = LocalSpacing.current
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid
    )
    val coroutineScope = rememberCoroutineScope()
    var visibleMonth by rememberSaveable { mutableStateOf(calendarState.firstVisibleMonth) }
    LaunchedEffect(calendarState) {
        snapshotFlow { calendarState.layoutInfo.completelyVisibleMonths.firstOrNull() }
            .filterNotNull()
            .collect { month ->
                if (visibleMonth != month)
                {
                    visibleMonth = month
                    viewModel.onCurrentDayChange(null)
                }
            }
    }

    LaunchedEffect(key1 = uiState) {
        bookings = if (uiState is UiState.Success)
            (uiState as UiState.Success).data?.bookings ?: emptyMap()
        else emptyMap()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(calendarBackgroundColor)
    ) {
        item {
            LaunchedEffect(calendarState) {
                snapshotFlow { calendarState.layoutInfo.completelyVisibleMonths.firstOrNull() }
                    .filterNotNull()
                    .collect { month -> visibleMonth = month }
            }
            Spacer(Modifier.height(spacing.spaceMedium))
            SimpleCalendarTitle(
                modifier = Modifier,
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = onToolBarColor,
                buttonBackgroundColor = bookingsContentBackgroundColor,
                buttonContentColor = bookingsContentColor
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            MonthHeader(
                daysOfWeek = daysOfWeek,
                contentColor = calendarContentColor,
                backgroundColor = calendarBackgroundColor,
                modifier = Modifier.padding(vertical = spacing.spaceMedium)
            )
            HorizontalCalendar(
                modifier = Modifier.wrapContentWidth(),
                state = calendarState,
                dayContent = { day ->
                    val colors = if (day.position == DayPosition.MonthDate) {
                        bookings[day.date].orEmpty().map { MaterialTheme.colorScheme.tertiary }
                    } else {
                        emptyList()
                    }
                    Day(
                        day = day,
                        isSelected = selection == day,
                        colors = colors,
                        onClick = { calendarDay ->
                            viewModel.onCurrentDayChange(calendarDay)
                        },
                        selectedBackgroundColor = selectedDayBackgroundColor,
                        unselectedBackgroundColor = calendarBackgroundColor,
                        selectedContentColor = selectedDayContentColor,
                        unselectedContentColor = calendarContentColor,
                    )
                }
            )
        }
        if (state is UiState.Success) {
            (state as UiState.Success<CalendarUiState>).data?.let {
                item {
                    selection?.let { localDate ->
                        if (it.bookingsForDay.isNotEmpty())
                            Text(
                                text = formatDate(localDate.date),
                                color = bookingsTitleContentColor,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = spacing.spaceMedium)
                                    .clip(RoundedCornerShape(spacing.spaceSmall))
                                    .background(bookingsTitleBackgroundColor)
                                    .padding(spacing.spaceSmall),
                                style = MaterialTheme.typography.titleMedium
                            )
                    }
                }
                items(items = it.bookingsForDay) { booking ->
                    BookingItem(
                        item = booking,
                        contentColor = bookingsContentColor,
                        backgroundColor = bookingsContentBackgroundColor
                    )
                    Spacer(Modifier.height(spacing.spaceSmall))
                }
            }
        }
    }
}

fun formatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return localDate.format(formatter)
}

private
val CalendarLayoutInfo.completelyVisibleMonths: List<CalendarMonth>
    get() {
        val visibleItemsInfo = this.visibleMonthsInfo.toMutableList()
        return if (visibleItemsInfo.isEmpty()) {
            emptyList()
        } else {
            val lastItem = visibleItemsInfo.last()
            val viewportSize = this.viewportEndOffset + this.viewportStartOffset
            if (lastItem.offset + lastItem.size > viewportSize) {
                visibleItemsInfo.removeLast()
            }
            val firstItem = visibleItemsInfo.firstOrNull()
            if (firstItem != null && firstItem.offset < this.viewportStartOffset) {
                visibleItemsInfo.removeFirst()
            }
            visibleItemsInfo.map { it.month }
        }
    }
