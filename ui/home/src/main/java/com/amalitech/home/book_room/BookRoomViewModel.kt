package com.amalitech.home.book_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.book_room.model.Booking
import com.amalitech.home.book_room.use_case.BookRoomUseCase
import com.amalitech.home.book_room.util.toBookRoomUi
import com.amalitech.ui.home.R
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class BookRoomViewModel(
    private val bookRoomUseCase: BookRoomUseCase
) : BaseViewModel<RoomUi>() {
    private val _userInput = mutableStateOf(UserInput())
    val userInput: State<UserInput> get() = _userInput

    private val globalStartTime = LocalTime.of(9, 0)
    private val globalEndTime = LocalTime.of(18, 0)
    private val globalInterval = Duration.ofMinutes(15)

    private val _slotManager = mutableStateOf(SlotSelectionManager())
    val slotManager: State<SlotSelectionManager> get() = _slotManager

    fun onShowStartTimesRequest(room: RoomUi) {
        val date = _userInput.value.date
        if (date == null) {
            _uiStateFlow.update {
                UiState.Error(UiText.StringResource(R.string.error_select_date))
            }
        } else {
            getAvailableStartTimes(room, date)
            _slotManager.value = _slotManager.value.copy(
                canShowStartTimes = true
            )
        }
    }

    fun isDateAvailable(date: LocalDate, room: RoomUi): Boolean {
        val bookingForDate = room.bookings.filter { it.date == date }
        val length = bookingForDate.size - 1
        if (bookingForDate.isEmpty())
            return true
        bookingForDate.forEachIndexed { index, bookingUi ->
            if (index < length)
                if (Duration.between(
                        bookingForDate[index].endTime,
                        bookingForDate[index + 1].startTime
                    ).toMinutes() >= globalInterval.toMinutes()
                ) {
                    return true
                }
            if (index == length)
                if (Duration.between(bookingUi.endTime, globalEndTime) >= globalInterval)
                    return true
        }
        return false
    }

    fun onAttendeeNewValue(value: String) {
        _userInput.value = _userInput.value.copy(attendee = value)
    }

    fun getBookableRoom(id: String) {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val result = bookRoomUseCase.getBookableRoom(id)

            if (result.data != null) {
                _uiStateFlow.update {
                    UiState.Success(result.data!!.toBookRoomUi())
                }
            } else if (result.error != null) {
                _uiStateFlow.update {
                    UiState.Error(result.error)
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(UiText.StringResource(com.amalitech.core_ui.R.string.generic_error))
                }
            }
        }
    }

    private fun getAvailableStartTimes(room: RoomUi, date: LocalDate) {
        val allTimes = generateAllTimes()
        val extract = room.bookings.filter { bookingUi -> bookingUi.date == date }
        val bookedTimes = extract.flatMap { booking ->
            val startIdx = allTimes.indexOf(booking.startTime)
            val endIdx = allTimes.indexOf(booking.endTime)
            allTimes.subList(startIdx, endIdx)
        }
        _slotManager.value = _slotManager.value.copy(
            availableStartTimes = allTimes.filterNot { time -> bookedTimes.contains(time) }
        )
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

    fun onStopShowingStartTime() {
        _slotManager.value = _slotManager.value.copy(
            canShowStartTimes = false
        )
    }

    fun onShowEndTimeRequest(room: RoomUi) {
        val startTime = _userInput.value.startTime
        val date = _userInput.value.date
        if (startTime != null && date != null) {
            getAvailableEndTimes(room, startTime, date)
            _slotManager.value = _slotManager.value.copy(
                canShowEndTimes = true
            )
        } else {
            _uiStateFlow.update {
                UiState.Error(UiText.StringResource(R.string.error_select_start_time))
            }
        }
    }

    private fun getAvailableEndTimes(room: RoomUi, meetingStartTime: LocalTime, date: LocalDate) {
        val availableEndTimes: MutableList<LocalTime> = mutableListOf()
        val firstNextMeeting = room.bookings.filter { bookingUi -> bookingUi.date == date }
            .sortedBy { it.startTime.toSecondOfDay() }
            .firstOrNull { meetingStartTime.isBefore(it.startTime) }?.startTime
        var startTime = meetingStartTime

        while (startTime.isBefore(firstNextMeeting ?: globalEndTime)) {
            startTime = startTime.plusMinutes(globalInterval.toMinutes())
            availableEndTimes.add(startTime)
        }
        _slotManager.value = _slotManager.value.copy(
            availableEndTimes = availableEndTimes
        )
    }

    fun onStopShowingEndTime() {
        _slotManager.value = _slotManager.value.copy(
            canShowEndTimes = false
        )
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

    fun onAddAttendee() {
        val attendeeValue = _userInput.value.attendee.trim()
        if (attendeeValue.isNotBlank()) {
            val attendees = userInput.value.attendees.toMutableList()
            attendees.add(attendeeValue)
            _userInput.value = userInput.value.copy(
                attendees = attendees
            )
            _userInput.value = _userInput.value.copy(attendee = "")
        }
    }

    fun onDeleteAttendee(attendee: String) {
        val attendees: MutableList<String> = _userInput.value.attendees.toMutableList()
        attendees.remove(attendee)
        _userInput.value = _userInput.value.copy(
            attendees = attendees
        )
    }

    fun onBook(roomId: String) {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            var error: UiText?
            _uiStateFlow.update {
                UiState.Loading()
            }
            for (attendee in _userInput.value.attendees) {
                error = bookRoomUseCase.validateEmail(attendee)
                if (error != null) {
                    _uiStateFlow.update {
                        UiState.Error(error)
                    }
                    break
                }
            }
            if (_uiStateFlow.value !is UiState.Error) {
                val isStartTimeValid = _userInput.value.startTime != null
                val isEndTimeValid = _userInput.value.endTime != null
                val isDateValid = _userInput.value.date != null
                val isAttendeesValid = _userInput.value.attendees.isNotEmpty()

                when {
                    !isAttendeesValid -> {
                        updateStateWithError(UiText.StringResource(R.string.error_no_attendee_added))
                    }

                    !isEndTimeValid -> {
                        updateStateWithError(UiText.StringResource(R.string.error_provide_an_end_time))
                    }

                    !isStartTimeValid -> {
                        updateStateWithError(UiText.StringResource(R.string.error_provide_start_time))
                    }

                    !isDateValid -> {
                        updateStateWithError(UiText.StringResource(R.string.error_provide_date_for_the_meeting))
                    }

                    else -> {
                        val booking = Booking(
                            roomId = roomId,
                            attendees = _userInput.value.attendees,
                            note = _userInput.value.note
                        )
                        val result = bookRoomUseCase.bookRoom(booking)
                        if (result != null) {
                            _uiStateFlow.update {
                                UiState.Error(result)
                            }
                        } else {
                            _uiStateFlow.update {
                                UiState.Success(RoomUi(canNavigate = true))
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * updateStateWithError - adds error to the state
     * @param error The error to be added
     */
    internal fun updateStateWithError(error: UiText?) {
        _uiStateFlow.update {
            UiState.Error(error)
        }
    }

    fun onNoteValueChanged(note: String) {
        _userInput.value = _userInput.value.copy(
            note = note
        )
    }

    fun onStartTimeSelected(startTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            startTime = startTime
        )
    }

    fun onEndTimeSelected(endTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            endTime = endTime
        )
    }
}
