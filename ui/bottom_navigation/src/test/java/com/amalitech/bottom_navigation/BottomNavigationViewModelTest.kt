package com.amalitech.bottom_navigation

import com.amalitech.bottom_navigation.use_case.GetInvitationsNumber
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BottomNavigationViewModelTest {

    private lateinit var viewModel: BottomNavigationViewModel

    @MockK
    private lateinit var getInvitationsNumber: GetInvitationsNumber

    @Before
    fun setUp() {
        getInvitationsNumber = mockk()

        every {
            getInvitationsNumber()
        } returns 6

        viewModel = BottomNavigationViewModel(getInvitationsNumber)
    }

    @Test
    fun `ensure invitations number is fetched`() {

        assertEquals(6, viewModel.invitations.value)
    }
}
