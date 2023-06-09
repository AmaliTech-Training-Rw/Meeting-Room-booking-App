package com.amalitech.onboarding.forgot_password

import com.amalitech.core.util.UiText
import com.amalitech.core.R
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
    fun `when a snackBar is shown, its value is cleared in state`() {
        viewModel.onSnackBarShown()

        assertEquals(null, viewModel.uiState.value.snackBarValue)
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
        assertEquals(
            UiText.StringResource(R.string.link_sent_inbox),
            viewModel.uiState.value.snackBarValue
        )
        assertEquals(
            true,
            viewModel.uiState.value.linkSent
        )
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
        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            viewModel.uiState.value.error
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
        assertEquals(
            UiText.StringResource(R.string.error_email_not_valid),
            viewModel.uiState.value.error
        )
    }
}
