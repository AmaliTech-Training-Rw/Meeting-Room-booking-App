package com.amalitech.onboarding.reset_password

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ResetPasswordViewModelTest {
    private lateinit var viewModel: ResetPasswordViewModel

    @MockK
    private lateinit var resetPasswordUseCase: ResetPasswordUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        resetPasswordUseCase = mockk()
        viewModel = ResetPasswordViewModel(
            resetPasswordUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when a new password is entered, its value is held in state`() {
        // GIVEN - a password and an OnNewPassword event
        val newPassword = "password"

        // WHEN - onNewPassword is called
        viewModel.onNewPassword(newPassword)

        // THEN - state holds the value of our new newPassword
        assertEquals(newPassword, viewModel.uiState.value.newPassword)
    }

    @Test
    fun `when a new password confirmation is entered, its value is held in state`() {
        // GIVEN - a password and an OnNewPassword event
        val confirmationPassword = "password"

        every {
            resetPasswordUseCase.checkPasswordsMatch(any(), any())
        } returns null

        // WHEN - onResetPassword is called
        viewModel.onNewPasswordConfirmation(confirmationPassword)

        // THEN - state holds the value of our new newPassword address
        assertEquals(confirmationPassword, viewModel.uiState.value.passwordConfirmation)

    }

    @Test
    fun `when newPasswordConfirmation is called with passwords that don't match, an error is added to state`() {
        // GIVEN - different passwords
        val confirmationPassword = "password"
        val password = "different"
        every {
            resetPasswordUseCase.checkPasswordsMatch(any(), any())
        } returns UiText.StringResource(R.string.error_passwords_dont_match)

        // WHEN - onNewPassword and onNewPasswordConfirmation are called
        viewModel.onNewPassword(password)
        viewModel.onNewPasswordConfirmation(confirmationPassword)

        // THEN - state holds the value an error
        assertEquals(
            UiText.StringResource(R.string.error_passwords_dont_match),
            viewModel.uiState.value.error
        )
    }

    @Test
    fun `when onResetPassword is called and there is no errors, state is updated`() {
        every {
            resetPasswordUseCase.resetPassword(any(), any())
        } returns null

        every {
            resetPasswordUseCase.checkPasswordsMatch(any(), any())
        } returns null

        viewModel.onResetPassword()

        assertEquals(
            UiText.StringResource(R.string.password_reset_successfully),
            viewModel.uiState.value.snackbarValue
        )
        assertEquals(
            true,
            viewModel.uiState.value.passwordReset
        )
    }

    @Test
    fun `when onResetPassword is called with errors, state is updated`() {
        // GIVEN - a mock authentication use case that assume reset password fails
        every {
            resetPasswordUseCase.resetPassword(any(), any())
        } returns null

        every {
            resetPasswordUseCase.checkPasswordsMatch(any(), any())
        } returns UiText.StringResource(R.string.error_passwords_dont_match)

        // WHEN - onResetPassword is called
        viewModel.onResetPassword()

        // THEN - state is updated with the corresponding error
        assertEquals(
            UiText.StringResource(R.string.error_passwords_dont_match),
            viewModel.uiState.value.error
        )
    }
}
