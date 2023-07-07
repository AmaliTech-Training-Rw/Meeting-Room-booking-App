package com.amalitech.home.book_room

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.MainDispatcherRule
import com.amalitech.home.book_room.model.BookableRoom
import com.amalitech.home.book_room.use_case.BookRoomUseCase
import com.amalitech.home.book_room.util.toBookRoomUi
import com.amalitech.home.model.Booking
import com.amalitech.home.util.Response
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class BookRoomViewModelTest {

    private lateinit var viewModel: BookRoomViewModel

    @MockK
    private lateinit var useCase: BookRoomUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCase = mockk()
        viewModel = BookRoomViewModel(useCase)
    }

    @Test
    fun `ensures getAvailableStartTimes works`() {
        val selectedDate = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "Room 1",
            description = "Meeting Room",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(
                    startTime = LocalTime.of(10, 0),
                    endTime = LocalTime.of(12, 0),
                    date = selectedDate
                ),
                BookingUi(
                    startTime = LocalTime.of(14, 0),
                    endTime = LocalTime.of(16, 0),
                    date = selectedDate
                ),
                BookingUi(
                    startTime = LocalTime.of(17, 0),
                    endTime = LocalTime.of(18, 0),
                    date = LocalDate.now()
                ),

            )
        )

        viewModel.onSelectedDate(selectedDate)
        viewModel.onShowStartTimesRequest(room)


        val expected = listOf(
            LocalTime.of(9, 0),
            LocalTime.of(9, 15),
            LocalTime.of(9, 30),
            LocalTime.of(9, 45),
            LocalTime.of(12, 0),
            LocalTime.of(12, 15),
            LocalTime.of(12, 30),
            LocalTime.of(12, 45),
            LocalTime.of(13, 0),
            LocalTime.of(13, 15),
            LocalTime.of(13, 30),
            LocalTime.of(13, 45),
            LocalTime.of(16, 0),
            LocalTime.of(16, 15),
            LocalTime.of(16, 30),
            LocalTime.of(16, 45),
            LocalTime.of(17, 0),
            LocalTime.of(17, 15),
            LocalTime.of(17, 30),
            LocalTime.of(17, 45)
        )
        val availableTimes = viewModel.slotManager.value.availableStartTimes.filter { it.isAvailable }.map { it.time }
        assertEquals(expected, availableTimes)
    }

    @Test
    fun `ensures getAvailableEndTimes works when there is a meeting`() {
        val selectedDate = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "Room 1",
            description = "Meeting Room",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(LocalTime.of(10, 0), LocalTime.of(12, 0), selectedDate),
                BookingUi(LocalTime.of(14, 0), LocalTime.of(16, 0), selectedDate)
            )
        )

        viewModel.onSelectedDate(selectedDate)
        viewModel.onStartTimeSelected(LocalTime.of(8, 0))
        viewModel.onShowEndTimeRequest(room)

        val expected = listOf(
            LocalTime.of(9, 0),
            LocalTime.of(9, 15),
            LocalTime.of(9, 30),
            LocalTime.of(9, 45),
            LocalTime.of(10, 0)
        )
        val availableTimes = viewModel.slotManager.value.availableEndTimes.filter { it.isAvailable }.map { it.time }
        assertEquals(expected, availableTimes)
    }

    @Test
    fun `ensures getAvailableEndTimes works for a booking withing two meeting`() {
        val selectedDate = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "Room 1",
            description = "Meeting Room",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(LocalTime.of(10, 0), LocalTime.of(12, 0), selectedDate),
                BookingUi(LocalTime.of(14, 0), LocalTime.of(16, 0), selectedDate)
            )
        )

        viewModel.onSelectedDate(selectedDate)
        viewModel.onStartTimeSelected(LocalTime.of(12, 0))
        viewModel.onShowEndTimeRequest(room)

        val expected = listOf(
            LocalTime.of(12, 15),
            LocalTime.of(12, 30),
            LocalTime.of(12, 45),
            LocalTime.of(13, 0),
            LocalTime.of(13, 15),
            LocalTime.of(13, 30),
            LocalTime.of(13, 45),
            LocalTime.of(14, 0)
        )

        val availableTimes = viewModel.slotManager.value.availableEndTimes.filter { it.isAvailable }.map { it.time }
        assertEquals(expected,availableTimes)
    }

    @Test
    fun `ensures getAvailableEndTimes works when there is no meeting`() {
        val selectedDate = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "Room 1",
            description = "Meeting Room",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf()
        )

        viewModel.onSelectedDate(selectedDate)
        viewModel.onStartTimeSelected(LocalTime.of(15, 0))
        viewModel.onShowEndTimeRequest(room)

        val expected = listOf(
            LocalTime.of(15, 15),
            LocalTime.of(15, 30),
            LocalTime.of(15, 45),
            LocalTime.of(16, 0),
            LocalTime.of(16, 15),
            LocalTime.of(16, 30),
            LocalTime.of(16, 45),
            LocalTime.of(17, 0),
            LocalTime.of(17, 15),
            LocalTime.of(17, 30),
            LocalTime.of(17, 45),
            LocalTime.of(18, 0),
        )

        val availableTimes = viewModel.slotManager.value.availableEndTimes.filter { it.isAvailable }.map { it.time }
        assertEquals(expected, availableTimes)
    }

    @Test
    fun `ensures onNewStartTime works`() {
        val startTime = LocalTime.now()
        viewModel.onNewStartTime(startTime)

        assertEquals(startTime, viewModel.userInput.value.startTime)
    }

    @Test
    fun `ensures onNewEndTime works`() {
        val endTime = LocalTime.now()
        viewModel.onNewEndTime(endTime)

        assertEquals(endTime, viewModel.userInput.value.endTime)
    }

    @Test
    fun `ensures onSelectedDate works`() {
        val date = LocalDate.now()
        viewModel.onSelectedDate(date)

        assertEquals(date, viewModel.userInput.value.date)
        assertEquals(null, viewModel.userInput.value.startTime)
        assertEquals(null, viewModel.userInput.value.endTime)
    }

    @Test
    fun `ensures getBookableRoom works when the result is Success`() = runTest {
        val bookableRoom = BookableRoom(
            name = "Room",
            description = "description",
            features = listOf("Internet", "Drinks", "Air conditional"),
            bookings = listOf(
                Booking(
                    startTime = LocalDateTime.now(),
                    endTime = LocalDateTime.now().plusHours(1),
                    roomName = "room1"
                ),
                Booking(
                    startTime = LocalDateTime.now().plusHours(3),
                    endTime = LocalDateTime.now().plusHours(4),
                    roomName = "room2"
                )
            ),
            imgUrl = ""
        )

        coEvery {
            useCase.getBookableRoom(any())
        } returns Response(
            data = bookableRoom
        )
        viewModel.getBookableRoom("id")
        advanceUntilIdle()


        assertTrue(viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(bookableRoom.toBookRoomUi(), (viewModel.uiStateFlow.value as UiState.Success).data)
    }

    @Test
    fun `ensures getBookableRoom works when the result is Error`() = runTest {
        val error = UiText.StringResource(R.string.error_default_message)
        coEvery {
            useCase.getBookableRoom(any())
        } returns Response(
            error = error
        )
        viewModel.getBookableRoom("id")
        advanceUntilIdle()


        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(error, (viewModel.uiStateFlow.value as UiState.Error).error)
    }

    @Test
    fun `ensures that isDateAvailable returns false when there is no slot available`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "No Available Slots Room",
            description = "Meeting Room with No Available Slots",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(LocalTime.of(9, 0), LocalTime.of(12, 47), date),
                BookingUi(LocalTime.of(13, 0), LocalTime.of(15, 0), date),
                BookingUi(LocalTime.of(15, 12), LocalTime.of(16, 0), date),
                BookingUi(LocalTime.of(16, 7), LocalTime.of(18, 0), date)
            )
        )

        val isAvailable = viewModel.isDateAvailable(date, room)

        assertTrue(!isAvailable)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is available slots`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "No Available Slots Room",
            description = "Meeting Room with No Available Slots",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(LocalTime.of(9, 0), LocalTime.of(12, 45), date),
                BookingUi(LocalTime.of(13, 0), LocalTime.of(15, 0), date),
                BookingUi(LocalTime.of(15, 0), LocalTime.of(16, 0), date),
                BookingUi(LocalTime.of(16, 0), LocalTime.of(18, 0), date)
            )
        )

        val isAvailable = viewModel.isDateAvailable(date, room)

        assertTrue(isAvailable)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is available slots after the last booking`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "No Available Slots Room",
            description = "Meeting Room with No Available Slots",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf(
                BookingUi(LocalTime.of(9, 0), LocalTime.of(12, 59), date),
                BookingUi(LocalTime.of(13, 0), LocalTime.of(15, 0), date),
                BookingUi(LocalTime.of(15, 0), LocalTime.of(16, 0), date),
                BookingUi(LocalTime.of(16, 0), LocalTime.of(17, 45), date)
            )
        )

        val isAvailable = viewModel.isDateAvailable(date, room)

        assertTrue(isAvailable)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is no booking`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = RoomUi(
            name = "No Available Slots Room",
            description = "Meeting Room with No Available Slots",
            features = listOf("Projector", "Whiteboard"),
            bookings = listOf()
        )

        val isAvailable = viewModel.isDateAvailable(date, room)

        assertTrue(isAvailable)
    }

    @Test
    fun `ensures onAttendeeNewValue works`() {
        val attendee = "nkamdaou@gmail.com"

        viewModel.onAttendeeNewValue(attendee)

        assertEquals(attendee, viewModel.userInput.value.attendee)
    }
    @Test
    fun `ensures onStopShowingStartTime works`() {

        viewModel.onStopShowingStartTime()

        assertEquals(false, viewModel.slotManager.value.canShowStartTimes)
    }

    @Test
    fun `ensures onStopShowingEndTime works`() {

        viewModel.onStopShowingEndTime()

        assertEquals(false, viewModel.slotManager.value.canShowEndTimes)
    }

    @Test
    fun `ensures onAddAttendee works`() {
        val attendee = "nkamdaou@gmail.Com"
        viewModel.onAttendeeNewValue(attendee)

        viewModel.onAddAttendee()

        assertEquals(true, viewModel.userInput.value.attendees.contains(attendee))
    }

    @Test
    fun `ensures onDeleteAttendee works`() {
        val attendee = "nkamdaou@gmail.Com"
        viewModel.onAttendeeNewValue(attendee)
        viewModel.onAddAttendee()
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()

        viewModel.onDeleteAttendee(attendee)

        assertEquals(false, viewModel.userInput.value.attendees.contains(attendee))
    }

    @Test
    fun `ensures onBook works when every need parameter is provided`() {
        every {
            useCase.validateEmail(any())
        } returns null
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onSelectedDate(LocalDate.now())
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now().plusHours(4))

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(UiState.Success(RoomUi(canNavigate = true)), (viewModel.uiStateFlow.value) as UiState.Success)
    }

    @Test
    fun `ensures onBook triggers an error when attendees are not email type`() {
        val error = UiText.StringResource(R.string.error_email_not_valid)
        every {
            useCase.validateEmail(any())
        } returns error
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewEndTime(LocalTime.now())
        viewModel.onNewStartTime(LocalTime.now().plusHours(4))
        viewModel.onSelectedDate(LocalDate.now())

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }

    @Test
    fun `ensures onBook triggers an error when startTime is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.home.R.string.error_provide_start_time)
        every {
            useCase.validateEmail(any())
        } returns null
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onSelectedDate(LocalDate.now())
        viewModel.onAddAttendee()
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }

    @Test
    fun `ensures onBook triggers an error when endTime is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.home.R.string.error_provide_an_end_time)
        every {
            useCase.validateEmail(any())
        } returns null
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onSelectedDate(LocalDate.now())

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }

    @Test
    fun `ensures onBook triggers an error when date is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.home.R.string.error_provide_date_for_the_meeting)
        every {
            useCase.validateEmail(any())
        } returns null
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }

    @Test
    fun `ensures onBook triggers an error when there is no attendee`() {
        val error = UiText.StringResource(com.amalitech.ui.home.R.string.error_no_attendee_added)
        every {
            useCase.validateEmail(any())
        } returns null
        coEvery {
            useCase.bookRoom(any())
        } returns null
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }

    @Test
    fun `ensures onNoteValueChanged works`() {
        val note = "note"

        viewModel.onNoteValueChanged(note)

        assertEquals(note, viewModel.userInput.value.note)
    }

    @Test
    fun `ensures onEndTimeSelected works`() {
        val endTime = LocalTime.now()

        viewModel.onEndTimeSelected(endTime)

        assertEquals(endTime, viewModel.userInput.value.endTime)
    }

    @Test
    fun `ensures onStartTimeSelected works`() {
        val startTime = LocalTime.now()

        viewModel.onStartTimeSelected(startTime)

        assertEquals(startTime, viewModel.userInput.value.startTime)
        assertEquals(null, viewModel.userInput.value.endTime)
    }

    @Test
    fun `ensures updateStateWithError works`() {
        val error = UiText.StringResource(R.string.error_email_not_valid)

        viewModel.updateStateWithError(error)

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(UiState.Error<RoomUi>(error), (viewModel.uiStateFlow.value) as UiState.Error)
    }
}
