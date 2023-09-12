package com.amalitech.booking.requests

import com.amalitech.booking.MainDispatcherRule
import com.amalitech.booking.model.Booking
import com.amalitech.booking.request.use_case.BookingRequestsUseCaseWrapper
import com.amalitech.core.util.ApiResult
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class BookingRequestViewModelTest {

    private lateinit var viewModel: BookingRequestViewModel

    @MockK
    private lateinit var useCaseWrapper: BookingRequestsUseCaseWrapper

    @get:Rule
    val mainCoroutineDispatcher = MainDispatcherRule()

    private val bookings = listOf(
        Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeholder.com/200.png",
            "Ngomde Cadet Kamdaou"
        ), Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeholder.com/200.png",
            "Ngomde Cadet Kamdaou"
        ), Booking(
            id = "id",
            "room 1",
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusHours(4),
            "https://via.placeholder.com/200.png",
            "Ngomde Cadet Kamdaou"
        )
    )

    @Before
    fun setUp() = runTest {
        useCaseWrapper = mockk()
        coEvery {
            useCaseWrapper.fetchBookingsUseCase()
        } returns ApiResult(data = bookings)
        viewModel = BookingRequestViewModel(useCaseWrapper)
        // Make sure the fetchBookings invocation in the init block
        // has finished fetching
        advanceUntilIdle()
    }

    @Test
    fun `ensure fetchBookings works when there is no errors`() = runTest {
        coEvery {
            useCaseWrapper.fetchBookingsUseCase()
        } returns ApiResult(
            data = bookings
        )

        val bookingUiState = BookingRequestsUiState(
            bookings = bookings,
        )
        viewModel.fetchBookings()
        advanceUntilIdle()
        assertEquals(bookingUiState, viewModel.uiState.value)
    }
}
