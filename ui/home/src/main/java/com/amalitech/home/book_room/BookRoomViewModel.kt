package com.amalitech.home.book_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.AuthenticationBaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.book_room.use_case.BookRoomUseCase
import com.amalitech.home.book_room.util.toBookRoomUi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class BookRoomViewModel(
    private val bookRoomUseCase: BookRoomUseCase
) : AuthenticationBaseViewModel<RoomUi>() {
    private val _userInput = mutableStateOf(UserInput())
    val userInput: State<UserInput> get() = _userInput

    private val globalStartTime = LocalTime.of(9, 0)
    private val globalEndTime = LocalTime.of(18, 0)
    private val globalInterval = Duration.ofMinutes(15)

    fun getBookableRoom(id: String) {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            baseResult.update {
                UiState.Loading()
            }
            val result = bookRoomUseCase.getBookableRoom(id)

            if (result.data != null) {
                baseResult.update {
                    UiState.Success(result.data!!.toBookRoomUi())
                }
            } else if (result.error != null) {
                baseResult.update {
                    UiState.Error(result.error)
                }
            } else {
                baseResult.update {
                    UiState.Error(UiText.StringResource(com.amalitech.core_ui.R.string.generic_error))
                }
            }
        }
    }

    fun getAvailableEndTimes(room: RoomUi, meetingStartTime: LocalTime): List<LocalTime> {
        val availableEndTimes: MutableList<LocalTime> = mutableListOf()
        val firstNextMeeting = room.bookings.sortedBy { it.startTime.toSecondOfDay() }
            .firstOrNull { meetingStartTime.isBefore(it.startTime) }?.startTime
        var startTime = meetingStartTime

        while(startTime.isBefore(firstNextMeeting ?: globalEndTime)) {
            startTime = startTime.plusMinutes(globalInterval.toMinutes())
            availableEndTimes.add(startTime)
        }
        return availableEndTimes
    }

    fun onSelectedDate(date: LocalDate) {
        _userInput.value = _userInput.value.copy(
            date = date
        )
    }

    fun onNewStartTime(startTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            startTime = startTime
        )
    }

    fun onNewEndTime(endTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            endTime = endTime
        )
    }

    fun getAvailableStartTimes(room: RoomUi): List<LocalTime> {
        val allTimes = generateAllTimes()
        val bookedTimes = room.bookings.flatMap { booking ->
            val startIdx = allTimes.indexOf(booking.startTime)
            val endIdx = allTimes.indexOf(booking.endTime)
            allTimes.subList(startIdx, endIdx)
        }
        return allTimes.filterNot { time -> bookedTimes.contains(time) }
    }
    private fun generateAllTimes(): List<LocalTime> {
        val allTimes = mutableListOf<LocalTime>()

        var currentTime = globalStartTime
        while (currentTime.isBefore(globalEndTime)) {
            allTimes.add(currentTime)
            currentTime = currentTime.plus(globalInterval)
        }

        return allTimes
    }

    fun isDateAvailable(date: LocalDate, room: RoomUi): Boolean {
        val bookingForDate = room.bookings.filter { it.date == date }
        val length = bookingForDate.size - 1
        if (bookingForDate.isEmpty())
            return true
        bookingForDate.forEachIndexed { index, bookingUi ->
            if (index < length)
                if (Duration.between(bookingForDate[index].endTime, bookingForDate[index + 1].startTime).toMinutes() >= globalInterval.toMinutes()) {
                    return true
                }
            if (index == length)
                if (Duration.between(bookingUi.endTime, globalEndTime) >= globalInterval)
                    return true
        }
        return false
    }
}
