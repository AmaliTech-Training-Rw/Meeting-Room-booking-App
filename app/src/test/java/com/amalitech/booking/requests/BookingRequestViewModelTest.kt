package com.amalitech.booking.requests

import com.amalitech.booking.request.use_case.BookingRequestsUseCaseWrapper
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class BookingRequestViewModelTest {

    private lateinit var viewModel: BookingRequestViewModel

    @MockK
    private lateinit var useCaseWrapper: BookingRequestsUseCaseWrapper

    @Before
    fun setUp() {
        useCaseWrapper = mockk()
        viewModel = BookingRequestViewModel(useCaseWrapper)
    }

    @Test
    fun rien() {
        val result = viewModel.lengthOfLongestSubstring("abcabcbb")
        assertEquals(3, result)
    }
}