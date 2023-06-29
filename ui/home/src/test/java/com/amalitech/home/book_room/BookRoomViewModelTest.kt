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

    private lateinit var bookRoomViewModel: BookRoomViewModel

    @MockK
    private lateinit var bookRoomUseCase: BookRoomUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        bookRoomUseCase = mockk()
        bookRoomViewModel = BookRoomViewModel(bookRoomUseCase)
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
                )
            )
        )

        bookRoomViewModel.onSelectedDate(selectedDate)
        val result = bookRoomViewModel.getAvailableStartTimes(room)


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
        assertEquals(expected, result)
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

        bookRoomViewModel.onSelectedDate(selectedDate)
        val result = bookRoomViewModel.getAvailableEndTimes(room, LocalTime.of(8, 0))

        val expected = listOf(
            LocalTime.of(8, 15),
            LocalTime.of(8, 30),
            LocalTime.of(8, 45),
            LocalTime.of(9, 0),
            LocalTime.of(9, 15),
            LocalTime.of(9, 30),
            LocalTime.of(9, 45),
            LocalTime.of(10, 0)
        )

        assertEquals(expected, result)
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

        bookRoomViewModel.onSelectedDate(selectedDate)
        val result = bookRoomViewModel.getAvailableEndTimes(room, LocalTime.of(12, 0))

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

        assertEquals(expected, result)
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

        bookRoomViewModel.onSelectedDate(selectedDate)
        val result = bookRoomViewModel.getAvailableEndTimes(room, LocalTime.of(15, 0))

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

        assertEquals(expected, result)
    }

    @Test
    fun `ensures onNewStartTime works`() {
        val startTime = LocalTime.now()
        bookRoomViewModel.onNewStartTime(startTime)

        assertEquals(startTime, bookRoomViewModel.userInput.value.startTime)
    }

    @Test
    fun `ensures onNewEndTime works`() {
        val endTime = LocalTime.now()
        bookRoomViewModel.onNewEndTime(endTime)

        assertEquals(endTime, bookRoomViewModel.userInput.value.endTime)
    }

    @Test
    fun `ensures onSelectedDate works`() {
        val date = LocalDate.now()
        bookRoomViewModel.onSelectedDate(date)

        assertEquals(date, bookRoomViewModel.userInput.value.date)
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
            )
        )

        coEvery {
            bookRoomUseCase.getBookableRoom(any())
        } returns Response(
            data = bookableRoom
        )
        bookRoomViewModel.getBookableRoom("id")
        advanceUntilIdle()


        assertTrue(bookRoomViewModel.publicBaseResult.value is UiState.Success)
        assertEquals(bookableRoom.toBookRoomUi(), (bookRoomViewModel.publicBaseResult.value as UiState.Success).data)
    }

    @Test
    fun `ensures getBookableRoom works when the result is Error`() = runTest {
        val error = UiText.StringResource(R.string.error_default_message)
        coEvery {
            bookRoomUseCase.getBookableRoom(any())
        } returns Response(
            error = error
        )
        bookRoomViewModel.getBookableRoom("id")
        advanceUntilIdle()


        assertTrue(bookRoomViewModel.publicBaseResult.value is UiState.Error)
        assertEquals(error, (bookRoomViewModel.publicBaseResult.value as UiState.Error).error)
    }
}
