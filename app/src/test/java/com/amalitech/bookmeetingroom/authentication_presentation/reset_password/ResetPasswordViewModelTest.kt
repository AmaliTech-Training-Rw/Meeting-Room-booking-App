package com.amalitech.bookmeetingroom.authentication_presentation.reset_password

import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.util.UiText
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResetPasswordViewModelTest {
    private lateinit var viewModel: ResetPasswordViewModel

    @MockK
    private lateinit var authenticationUseCase: AuthenticationUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        authenticationUseCase = mockk()
        viewModel = ResetPasswordViewModel(
            authenticationUseCase, testDispatcher
        )
    }

    @Test
    fun onEvent_newPassword_stateIsUpdated() {
        // GIVEN - a password and an OnNewPassword event
        val newPassword = "password"
        val event = ResetPasswordEvent.OnNewPassword(newPassword)

        // WHEN - onEvent is called
        viewModel.onEvent(event)

        // THEN - state holds the value of our new newPassword address
        assertEquals(newPassword, viewModel.state.newPassword)
    }

    @Test
    fun onEvent_confirmationPassword_stateIsUpdated() {
        // GIVEN - a password and an OnNewPassword event
        val confirmationPassword = "password"
        val event = ResetPasswordEvent.OnConfirmNewPassword(confirmationPassword)
        every {
            authenticationUseCase.checkPasswordsMatch(any(), any())
        } returns null

        // WHEN - onEvent is called
        viewModel.onEvent(event)

        // THEN - state holds the value of our new newPassword address
        assertEquals(confirmationPassword, viewModel.state.confirmNewPassword)
    }


    @Test
    fun onEvent_confirmationPasswordDifferentFromPassword_stateIsUpdated() {
        // GIVEN - a password and an OnNewPassword event
        val confirmationPassword = "password"
        val password = "different"
        val eventPassword = ResetPasswordEvent.OnNewPassword(password)
        val eventConfirmation = ResetPasswordEvent.OnConfirmNewPassword(confirmationPassword)
        every {
            authenticationUseCase.checkPasswordsMatch(any(), any())
        } returns UiText.StringResource(R.string.error_passwords_dont_match)

        // WHEN - onEvent is called
        viewModel.onEvent(eventPassword)
        viewModel.onEvent(eventConfirmation)

        // THEN - state holds the value of our new newPassword address
        assertEquals(UiText.StringResource(R.string.error_passwords_dont_match), viewModel.state.error)
    }



}