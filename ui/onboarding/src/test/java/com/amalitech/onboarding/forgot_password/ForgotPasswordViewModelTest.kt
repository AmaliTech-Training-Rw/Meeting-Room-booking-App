package com.amalitech.onboarding.forgot_password

import com.amalitech.core.util.UiText
import com.amalitech.core.R
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.forgot_password.use_case.ForgotPasswordUseCase
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
class ForgotPasswordViewModelTest {

    private lateinit var viewModel: ForgotPasswordViewModel

    @MockK
    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        forgotPasswordUseCase = mockk()
        viewModel = ForgotPasswordViewModel(
            forgotPasswordUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on new email called with any string state is updated`() {
        val email = "test@email.com "

        viewModel.onNewEmail(email)

        assertEquals(email.trim(), viewModel.uiState.value.email)
    }

    @Test
    fun `when sendResetLink is called with valid email address, state is updated accordingly`() {
        // GIVEN - a mock authentication use case that assume email is valid and onResetLinkEvent event
        every {
            forgotPasswordUseCase.validateEmail(any())
        } returns null

        every {
            forgotPasswordUseCase.sendResetLink(any())
        } returns null

        // WHEN - onSendResetLink is called
        viewModel.onSendResetLink()

        // THEN - two states are updated
        val state = viewModel.baseResult
        assertTrue(state.value is UiState.Success)
    }

    @Test
    fun `when sendResetLink is called with an email address that does not exist in DB, an error is added to state`() {
        // GIVEN - a mock authentication use case that assume email does not exist in DB
        every {
            forgotPasswordUseCase.validateEmail(any())
        } returns null

        every {
            forgotPasswordUseCase.sendResetLink(any())
        } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)

        // WHEN - onSendResetLink is called
        viewModel.onSendResetLink()

        // THEN - state holds the value of an error
        val state = viewModel.baseResult
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            (state.value as UiState.Error).error
        )
    }

    @Test
    fun `Ensures that an error is added to state when the email address is invalid`() {
        // GIVEN - a mock authentication use case that assume email does not exist in DB
        every {
            forgotPasswordUseCase.validateEmail(any())
        } returns UiText.StringResource(R.string.error_email_not_valid)

        // WHEN - onSendResetLink is called
        viewModel.onSendResetLink()

        // THEN - state holds the value of an error
        val state = viewModel.baseResult
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(R.string.error_email_not_valid),
            (state.value as UiState.Error).error
        )
    }
}
