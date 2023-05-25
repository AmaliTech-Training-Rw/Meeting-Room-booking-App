package com.example.authentication

import com.amalitech.domain.authentication.R
import com.example.authentication.login.LoginViewModel
import com.example.authentication.use_case.LoginUseCase
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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        loginUseCase = mockk()
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onNewEmail_anyString_stateUpdated() {
        val email = "test@email.com "

        viewModel.onNewEmail(email)

        assertEquals(email.trim(), viewModel.uiState.value.email)
    }

    @Test
    fun onNewPassword_anyString_stateUpdated() {
        val password = "test "

        viewModel.onNewPassword(password)

        assertEquals(password.trim(), viewModel.uiState.value.password)
    }

    @Test
    fun onSnackBarShowed_stateUpdated() {
        viewModel.onSnackBarShown()

        assertEquals(null, viewModel.uiState.value.snackBarValue)
    }

    @Test
    fun onLoginClick_validEmailAndValidPassword_stateUpdatedAccordingly() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns null

        every {
            loginUseCase.logIn(any(), any())
        } returns null

        viewModel.onLoginClick()

        val state = viewModel.uiState
        assertEquals(
            true, state.value.finishedLoggingIn
        )
        assertEquals(
            UiText.StringResource(com.amalitech.ui.authentication.R.string.logged_in_successfully),
            state.value.snackBarValue
        )
    }

    @Test
    fun onLoginClick_validEmailAndInvalidPassword_errorAddedToState() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns UiText.StringResource(R.string.error_password_is_blank)

        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(R.string.error_password_is_blank),
            viewModel.uiState.value.error
        )
    }

    @Test
    fun onLoginClick_invalidEmailAndvalidPassword_errorAddedToState() {
        every {
            loginUseCase.validateEmail(any())
        } returns UiText.StringResource(R.string.error_email_not_valid)

        every {
            loginUseCase.validatePassword(any())
        } returns null

        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(R.string.error_email_not_valid),
            viewModel.uiState.value.error
        )
    }

    @Test
    fun onLoginClick_wrongCredentials_errorAddedToState() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns null

        every {
            loginUseCase.logIn(any(), any())
        } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)


        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            viewModel.uiState.value.error
        )
    }


}
