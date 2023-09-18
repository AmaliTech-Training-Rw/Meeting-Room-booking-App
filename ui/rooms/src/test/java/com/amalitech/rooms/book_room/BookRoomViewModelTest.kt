package com.amalitech.rooms.book_room

import com.amalitech.core.R
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.rooms.MainDispatcherRule
import com.amalitech.rooms.book_room.use_case.BookRoomUseCasesWrapper
import com.amalitech.rooms.book_room.util.toBookRoomUi
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
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class BookRoomViewModelTest {

    private lateinit var viewModel: BookRoomViewModel

    @MockK
    private lateinit var useCase: BookRoomUseCasesWrapper

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCase = mockk()
        viewModel = BookRoomViewModel(useCase)
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
        val room = Room(
            roomName = "Room",
            roomFeatures = listOf("Internet", "Drinks", "Air conditional"),
            id = "",
            numberOfPeople = 1,
            imageUrl = listOf(),
        )

        coEvery {
            useCase.getRoomUseCase(any())
        } returns ApiResult(
            data = room
        )
        viewModel.getRoom("id")
        advanceUntilIdle()

        assertEquals(room.toBookRoomUi(), viewModel.uiState.value.bookRoomUi)
    }

    @Test
    fun `ensures getBookableRoom works when the result is Error`() = runTest {
        val error = UiText.StringResource(R.string.error_default_message)
        coEvery {
            useCase.getRoomUseCase(any())
        } returns ApiResult(
            error = error
        )
        viewModel.getRoom("id")
        advanceUntilIdle()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is available slots`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = Room(
            roomName = "No Available Slots Room",
            roomFeatures = listOf("Projector", "Whiteboard"),
            id = "",
            numberOfPeople = 1,
            imageUrl = listOf(),
        )
        coEvery {
            useCase.getRoomUseCase(any())
        } returns ApiResult(data = room)
        viewModel.getRoom("")

        val isAvailable = viewModel.isDateAvailable(date)

        assertTrue(isAvailable)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is available slots after the last booking`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = Room(
            roomName = "No Available Slots Room",
            roomFeatures = listOf("Projector", "Whiteboard"),
            id = "",
            numberOfPeople = 1,
            imageUrl = listOf(),
        )
        coEvery {
            useCase.getRoomUseCase(any())
        } returns ApiResult(data = room)
        viewModel.getRoom("")

        val isAvailable = viewModel.isDateAvailable(date)

        assertTrue(isAvailable)
    }

    @Test
    fun `ensures that isDateAvailable returns true when there is no booking`() {
        val date = LocalDate.of(2023, 5, 1)
        val room = Room(
            roomName = "No Available Slots Room",
            roomFeatures = listOf("Projector", "Whiteboard"),
            id = "",
            numberOfPeople = 1,
            imageUrl = listOf(),
        )
        coEvery {
            useCase.getRoomUseCase(any())
        } returns ApiResult(data = room)
        viewModel.getRoom("")

        val isAvailable = viewModel.isDateAvailable(date)

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
            useCase.validateEmailUseCase(any())
        } returns null
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onSelectedDate(LocalDate.now())
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now().plusHours(4))

        viewModel.onBook("id")

        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(true, viewModel.uiState.value.bookRoomUi.canNavigate)
    }

    @Test
    fun `ensures onBook triggers an error when attendees are not email type`() {
        val error = UiText.StringResource(R.string.error_email_not_valid)
        every {
            useCase.validateEmailUseCase(any())
        } returns error
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewEndTime(LocalTime.now())
        viewModel.onNewStartTime(LocalTime.now().plusHours(4))
        viewModel.onSelectedDate(LocalDate.now())

        viewModel.onBook("id")

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures onBook triggers an error when startTime is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.rooms.R.string.error_provide_start_time)
        every {
            useCase.validateEmailUseCase(any())
        } returns null
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onSelectedDate(LocalDate.now())
        viewModel.onAddAttendee()
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures onBook triggers an error when endTime is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.rooms.R.string.error_provide_an_end_time)
        every {
            useCase.validateEmailUseCase(any())
        } returns null
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onSelectedDate(LocalDate.now())

        viewModel.onBook("id")

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures onBook triggers an error when date is not set`() {
        val error = UiText.StringResource(com.amalitech.ui.rooms.R.string.error_provide_date_for_the_meeting)
        every {
            useCase.validateEmailUseCase(any())
        } returns null
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onAttendeeNewValue("test")
        viewModel.onAddAttendee()
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures onBook triggers an error when there is no attendee`() {
        val error = UiText.StringResource(com.amalitech.ui.rooms.R.string.error_no_attendee_added)
        every {
            useCase.validateEmailUseCase(any())
        } returns null
        coEvery {
            useCase.bookRoomUseCase(any())
        } returns null
        viewModel.onNewStartTime(LocalTime.now())
        viewModel.onNewEndTime(LocalTime.now())

        viewModel.onBook("id")

        assertEquals(error, viewModel.uiState.value.error)
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

        assertEquals(error, viewModel.uiState.value.error)
    }
}
