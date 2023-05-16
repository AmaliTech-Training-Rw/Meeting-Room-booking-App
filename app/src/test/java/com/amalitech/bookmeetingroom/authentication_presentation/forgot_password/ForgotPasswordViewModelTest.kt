package com.amalitech.bookmeetingroom.authentication_presentation.forgot_password

import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
import com.amalitech.bookmeetingroom.authentication_presentation.login.LoginEvent
import com.amalitech.bookmeetingroom.util.UiEvents
import com.amalitech.bookmeetingroom.util.UiText
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPasswordViewModelTest {

    private lateinit var viewModel: ForgotPasswordViewModel

    @MockK
    private lateinit var authenticationUseCase: AuthenticationUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        authenticationUseCase = mockk()
        viewModel = ForgotPasswordViewModel(
            authenticationUseCase, testDispatcher
        )
    }

    @Test
    fun onEvent_newEmail_stateIsUpdated() {
        // GIVEN - an email address and an OnNewEmail event
        val email = "user@test.com"
        val event = ForgotPasswordEvent.OnNewEmail(email)

        // WHEN - onEvent is called
        viewModel.onEvent(event)

        // THEN - state holds the value of our new email address
        assertEquals(email, viewModel.state.email)
    }

    @Test
    fun onEvent_sendResetLinkWithValidEmailAddress_eventsTriggered() =
        runBlocking {

            // GIVEN - a mock authentication use case that assume email is valid and onResetLinkEvent event
            val event = ForgotPasswordEvent.OnSendResetLink
            every {
                authenticationUseCase.validateEmail(any())
            } returns null

            every {
                authenticationUseCase.sendResetLink(any())
            } returns null

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - two events are triggered using a flow, ShowSnackBar and Navigate
            viewModel.uiEvent.take(2)
                .collect {
                    if (it is UiEvents.ShowSnackBar) {
                        assertEquals(
                            UiEvents.ShowSnackBar(
                                UiText.StringResource(R.string.link_sent_inbox)
                            ),
                            it
                        )
                    } else {
                        assertEquals(UiEvents.NavigateToHome, it)
                    }
                }
        }



    @Test
    fun onEvent_sendResetLinkWithUnExistingEmailAddress_stateUpdated() =
        runBlocking {

            // GIVEN - a mock authentication use case that assume email does not exist in DB and onResetLinkEvent event
            val event = ForgotPasswordEvent.OnSendResetLink
            every {
                authenticationUseCase.validateEmail(any())
            } returns null

            every {
                authenticationUseCase.sendResetLink(any())
            } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - state holds the value of an error
            assertEquals(UiText.StringResource(androidx.compose.ui.R.string.default_error_message), viewModel.state.error)
        }



    @Test
    fun onEvent_sendResetLinkWithInvalidEmailAddress_stateUpdated() =
        runBlocking {

            // GIVEN - a mock authentication use case that assume email does not exist in DB and onResetLinkEvent event
            val event = ForgotPasswordEvent.OnSendResetLink
            every {
                authenticationUseCase.validateEmail(any())
            } returns UiText.StringResource(R.string.error_email_not_valid)

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - state holds the value of an error
            assertEquals(UiText.StringResource(R.string.error_email_not_valid), viewModel.state.error)
        }



}