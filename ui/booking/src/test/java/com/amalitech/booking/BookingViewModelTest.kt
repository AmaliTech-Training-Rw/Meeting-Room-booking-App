package com.amalitech.booking

import com.amalitech.booking.model.Booking
import com.amalitech.booking.use_case.BookingUseCase
import com.amalitech.core.R
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.components.Tab
import com.amalitech.core_ui.util.UiState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class BookingViewModelTest {

    private lateinit var viewModel: BookingViewModel

    @MockK
    private lateinit var useCase: BookingUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCase = mockk()
        viewModel = BookingViewModel(useCase)
    }

    @Test
    fun `ensures fetchBookings update errors`() {
        val error = UiText.StringResource(R.string.error_default_message)
        coEvery {
            useCase.getBookingsUseCase(any())
        } returns ApiResult(error = error)

        viewModel.fetchBookings()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(error, (viewModel.uiStateFlow.value as UiState.Error).error)
    }

    @Test
    fun `ensures fetchBookings update on success`() {
        val date = LocalDate.now()
        val bookings = listOf(
            Booking(
                id = "id",
                "room1",
                date,
                LocalTime.of((0..23).random(), 0),
                LocalTime.of((0..23).random(), 0),
                "https://via.placeholder.com/500.png"
            ),
            Booking(
                id = "id",
                "room2",
                date,
                LocalTime.of((0..23).random(), 0),
                LocalTime.of((0..23).random(), 0),
                "https://via.placeholder.com/500.png"
            )
        )
        coEvery {
            useCase.getBookingsUseCase(any())
        } returns ApiResult(data = bookings)

        viewModel.fetchBookings()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(BookingUiState(bookings = bookings), (viewModel.uiStateFlow.value as UiState.Success).data)
    }

    @Test
    fun `ensures fetchBookings update on success with empty list`() {
        val bookings = emptyList<Booking>()
        coEvery {
            useCase.getBookingsUseCase(any())
        } returns ApiResult(data = bookings)

        viewModel.fetchBookings()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(BookingUiState(bookings = bookings), (viewModel.uiStateFlow.value as UiState.Success).data)
    }

    @Test
    fun `ensures onTabSelected works`() {
        val tab = Tab.EndedBookings

        viewModel.onTabSelected(tab)

        assertEquals(tab, viewModel.selectedTab.value)
    }
}
