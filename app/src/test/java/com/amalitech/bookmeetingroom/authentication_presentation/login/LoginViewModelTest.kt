package com.amalitech.bookmeetingroom.authentication_presentation.login

import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_domain.use_case.AuthenticationUseCase
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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var authenticationUseCase: AuthenticationUseCase

    private val mainDispatcherRule = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        authenticationUseCase = mockk()
        viewModel =
            LoginViewModel(authenticationUseCase, dispatchers = mainDispatcherRule)
    }

    @Test
    fun onEvent_onNewEmail_stateUpdated() {
        // GIVEN - an email address and an OnNewEmail event
        val email = "user@test.com"
        val event = LoginEvent.OnNewEmail(email)

        // WHEN - onEvent is called
        viewModel.onEvent(event)

        // THEN - state holds the value of our new email address
        assertEquals(email, viewModel.state.email)
    }

    @Test
    fun onEvent_onNewPassword_stateUpdated() {
        // GIVEN - an password and an OnNewPassword event
        val password = "password"
        val event = LoginEvent.OnNewPassword(password)

        // WHEN - onEvent is called
        viewModel.onEvent(event)

        // THEN - state holds the value of our new password address
        assertEquals(password, viewModel.state.password)
    }

    @Test
    fun onEvent_onLoginClickWithValidEmailAndPassword_noErrorTriggeredAndNavigateEventSent() =
        runBlocking {

            // GIVEN - a mock authentication use case that assume password and email are valid and onLoginClick event
            val event = LoginEvent.OnLoginClick
            every {
                authenticationUseCase.validateEmail(any())
            } returns null

            every {
                authenticationUseCase.validatePassword(any())
            } returns null

            every {
                authenticationUseCase.logIn(any(), any())
            } returns null

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - two events are triggered using a flow, ShowSnackBar and Navigate
            viewModel.uiEvent.take(2)
                .collect {
                    if (it is UiEvents.ShowSnackBar) {
                        assertEquals(
                            UiEvents.ShowSnackBar(
                                UiText.StringResource(R.string.logged_in_successfully)
                            ),
                            it
                        )
                    } else {
                        assertEquals(UiEvents.NavigateToHome, it)
                    }
                }
        }

    @Test
    fun onEvent_onLoginWithValidEmailAndInvalidPassword_anErrorIsAddedToState() =
        runBlocking {
            // GIVEN - a mock authentication use case that assume email is valid and password invalid and onLoginClick event
            val event = LoginEvent.OnLoginClick
            every {
                authenticationUseCase.validateEmail(any())
            } returns null

            every {
                authenticationUseCase.validatePassword(any())
            } returns UiText.StringResource(R.string.error_password_is_blank)

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - state holds the value of an error
            assertEquals(UiText.StringResource(R.string.error_password_is_blank), viewModel.state.error)
        }

    @Test
    fun onEvent_onLoginWithInvalidEmailAndValidPassword_anErrorIsAddedToState() =
        runBlocking {
            // GIVEN - a mock authentication use case that assume password is valid and email invalid and onLoginClick event
            val event = LoginEvent.OnLoginClick
            every {
                authenticationUseCase.validateEmail(any())
            } returns UiText.StringResource(R.string.error_email_not_valid)

            every {
                authenticationUseCase.validatePassword(any())
            } returns null

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - state holds the value of an error
            assertEquals(UiText.StringResource(R.string.error_email_not_valid), viewModel.state.error)
        }

    @Test
    fun onEvent_onLoginClickWithInvalidCredentials_anErrorIsAddedToState() =
        runBlocking {
            // GIVEN - a mock authentication use case that assume credentials are invalid and onLoginClick event
            val event = LoginEvent.OnLoginClick
            every {
                authenticationUseCase.validateEmail(any())
            } returns null

            every {
                authenticationUseCase.validatePassword(any())
            } returns null

            every {
                authenticationUseCase.logIn(any(), any())
            } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)

            // WHEN - onEvent is called
            viewModel.onEvent(event)

            // THEN - state holds the value of an error
            assertEquals(UiText.StringResource(androidx.compose.ui.R.string.default_error_message), viewModel.state.error)
        }
}
