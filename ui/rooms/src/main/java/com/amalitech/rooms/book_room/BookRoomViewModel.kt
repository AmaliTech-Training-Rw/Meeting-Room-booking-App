package com.amalitech.rooms.book_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.domain.model.Booking
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.rooms.book_room.use_case.BookRoomUseCasesWrapper
import com.amalitech.rooms.book_room.util.toBookRoomUi
import com.amalitech.rooms.model.Time
import com.amalitech.ui.rooms.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class BookRoomViewModel(
    private val bookRoomUseCasesWrapper: BookRoomUseCasesWrapper
) : BaseViewModel<RoomUiState>() {
    private val _userInput = mutableStateOf(BookRoomUserInput())
    val userInput: State<BookRoomUserInput> get() = _userInput
    private val _uiState = MutableStateFlow(RoomUiState())
    val uiState: StateFlow<RoomUiState> get() = _uiState.asStateFlow()

    private val globalEndTime = LocalTime.of(18, 0)
    private val globalInterval = Duration.ofMinutes(15)

    private val _slotManager = mutableStateOf(SlotSelectionManager())
    val slotManager: State<SlotSelectionManager> get() = _slotManager

    fun onShowStartTimesRequest(roomId: String) {
        viewModelScope.launch {
            val date = _userInput.value.date
            if (date == null) {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(R.string.error_select_date),
                        isLoading = false
                    )
                }
            } else {
                val result = bookRoomUseCasesWrapper.getStartTimeUseCase(roomId, date)
                result.data?.let { times ->
                    _slotManager.value = _slotManager.value.copy(
                        availableStartTimes = times.map { TimeUi(it.time, it.isAvailable) }
                    )
                    _slotManager.value = _slotManager.value.copy(
                        canShowStartTimes = true
                    )
                }
                result.error?.let {
                    _uiState.update { roomUiState ->
                        roomUiState.copy(error = it)
                    }
                }
            }
        }
    }

    fun isDateAvailable(date: LocalDate): Boolean {
        val room = _uiState.value.bookRoomUi
        room.let {
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
        }
        return false
    }

    fun onAttendeeNewValue(value: String) {
        _userInput.value = _userInput.value.copy(attendee = value)
    }

    fun getRoom(id: String) {
        if (job?.isActive == true)
            return
        job = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val result = bookRoomUseCasesWrapper.getRoomUseCase(id)

            if (result.data != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        bookRoomUi = result.data!!.toBookRoomUi(),
                        error = null
                    )
                }
            } else if (result.error != null) {
                _uiState.update {
                    it.copy(error = result.error, isLoading = false)
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(com.amalitech.core_ui.R.string.generic_error),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onStopShowingStartTime() {
        _slotManager.value = _slotManager.value.copy(
            canShowStartTimes = false
        )
    }

    fun onShowEndTimeRequest() {
        viewModelScope.launch {
            val startTime = _userInput.value.startTime
            val date = _userInput.value.date
            if (startTime != null && date != null) {
                val endTime = bookRoomUseCasesWrapper.getEndTimeUseCase(
                    startTime,
                    _slotManager.value.availableStartTimes.map { Time(it.time, it.isAvailable) }
                )
                endTime.data?.let { timeList ->
                    _slotManager.value = _slotManager.value.copy(
                        availableEndTimes = timeList.map { TimeUi(it.time, it.isAvailable) },
                        canShowEndTimes = true
                    )
                }
                endTime.error?.let { error ->
                    _uiState.update {
                        it.copy(
                            error = error,
                            isLoading = false
                        )
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(R.string.error_select_start_time),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onStopShowingEndTime() {
        _slotManager.value = _slotManager.value.copy(
            canShowEndTimes = false
        )
    }

    fun onSelectedDate(date: LocalDate) {
        _userInput.value = _userInput.value.copy(
            date = date,
            startTime = null,
            endTime = null
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
            _uiState.update {
                it.copy(isLoading = true)
            }
            for (attendee in _userInput.value.attendees) {
                error = bookRoomUseCasesWrapper.validateEmailUseCase(attendee)
                if (error != null) {
                    _uiState.update {
                        it.copy(error = error, isLoading = false)
                    }
                    break
                }
            }
            if (_uiState.value.error == null) {
                val isStartTimeValid = _userInput.value.startTime != null
                val isEndTimeValid = _userInput.value.endTime != null
                val isDateValid = _userInput.value.date != null
                val isAttendeesValid = _userInput.value.attendees.isNotEmpty()

                updateErrorOrBook(
                    isAttendeesValid,
                    isEndTimeValid,
                    isStartTimeValid,
                    isDateValid,
                    roomId
                )
            }
        }
    }

    private suspend fun updateErrorOrBook(
        isAttendeesValid: Boolean,
        isEndTimeValid: Boolean,
        isStartTimeValid: Boolean,
        isDateValid: Boolean,
        roomId: String
    ) {
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
                    note = _userInput.value.note,
                    startTime = _userInput.value.startTime!!,
                    endTime = _userInput.value.endTime!!,
                    date = _userInput.value.date!!
                )
                val result = bookRoomUseCasesWrapper.bookRoomUseCase(booking)
                if (result != null) {
                    _uiState.update {
                        it.copy(error = result, isLoading = false)
                    }
                } else {
                    _userInput.value = BookRoomUserInput()
                    _uiState.update {
                        it.copy(bookRoomUi = BookRoomUi(canNavigate = true), isLoading = false)
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
        _uiState.update {
            it.copy(error = error, isLoading = false)
        }
    }

    fun onNoteValueChanged(note: String) {
        _userInput.value = _userInput.value.copy(
            note = note
        )
    }

    fun onStartTimeSelected(startTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            startTime = startTime,
            endTime = null
        )
    }

    fun onEndTimeSelected(endTime: LocalTime) {
        _userInput.value = _userInput.value.copy(
            endTime = endTime
        )
    }

    fun onClearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
