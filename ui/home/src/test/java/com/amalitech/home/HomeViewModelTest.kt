package com.amalitech.home

import com.amalitech.core.domain.model.Booking
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.Response
import com.amalitech.core_ui.components.Tab
import com.amalitech.home.use_case.HomeUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var homeUseCase: HomeUseCase

    @MockK
    private lateinit var sharedPref: OnboardingSharedPreferences

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        homeUseCase = mockk()
        sharedPref = mockk()
        coEvery { homeUseCase.fetchBookings(any()) } returns Response(generateBookings())
        every { sharedPref.isUserAdmin() } returns true
        every { sharedPref.loadAdminUserScreen() } returns true
        viewModel = HomeViewModel(homeUseCase, sharedPref)
    }

    /*@Test
    fun `ensures value of current day is held by state`() {
        val currentDay = CalendarDay(
            date = LocalDate.now().plusDays(Random.nextLong(1, 28)),
            position = DayPosition.MonthDate
        )

        viewModel.onCurrentDayChange(currentDay)

        assertEquals(currentDay, viewModel.uiState.value.currentSelectedDate)
    }*/

    /*@Test
    fun `ensures bookings are updated with right values`() {
        val generatedBookings = generateBookings()
        val selectedDate = CalendarDay(
            date = generatedBookings.random().date,
            position = DayPosition.MonthDate
        )
        coEvery {
            homeUseCase.fetchBookings(any())
        } returns Response(generatedBookings)

        viewModel.onCurrentDayChange(selectedDate)
        viewModel.refreshBookings()

        val expected = viewModel.toBookingsUiStateMap(generatedBookings)
        assertTrue(viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(expected, (viewModel.uiStateFlow.value as UiState.Success).data?.bookings)
        assertEquals(expected[selectedDate.date], (viewModel.uiStateFlow.value as UiState.Success).data?.bookingsForDay)
    }*/

    @Test
    fun `ensures onSelectedTabChange works`() {
        val tab = Tab.createHomeTabsList().random()

        viewModel.onSelectedTabChange(tab)
        assertEquals(tab, viewModel.uiState.value.selectedTab)
    }

    private fun generateBookings(): List<Booking> {
        val roomNames = listOf("Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7")
        val startDate = LocalDate.of(2022, Month.JANUARY, 1)
        val endDate = LocalDate.of(2024, Month.DECEMBER, 31)

        val bookings = mutableListOf<Booking>()

        var currentDate = startDate
        while (bookings.size < 50 && currentDate.isBefore(endDate)) {
            val startTime = LocalTime.of((1..12).random(), 0)
            val endTime = startTime.plusHours((1..4).random().toLong())
            val roomName = roomNames.random()

            bookings.add(Booking(startTime, endTime, roomName, "id", emptyList(), "", LocalDate.now()))

            // Add another booking on the same date
            if (bookings.size < 50 && Math.random() < 0.5) {
                val startTime2 = LocalTime.of((13..23).random(), 0)
                val endTime2 = startTime2.plusHours((1..4).random().toLong())
                val roomName2 = roomNames.random()

                bookings.add(Booking(startTime2, endTime2, roomName2, "id", emptyList(), "", LocalDate.now()))
            }

            currentDate = currentDate.plusDays(1)
        }

        return bookings
    }
}
